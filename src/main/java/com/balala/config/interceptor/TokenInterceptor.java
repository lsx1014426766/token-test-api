package com.balala.config.interceptor;

import com.balala.base.ApiUtil;
import com.balala.config.myinterface.NotRepeatSubmit;
import com.balala.utils.MD5Util;
import com.balala.model.token.TokenInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Component
public class TokenInterceptor extends HandlerInterceptorAdapter {


	@Autowired
	private RedisTemplate redisTemplate;


	/**
	 *
	 * @param request
	 * @param response
	 * @param handler 访问的目标方法
	 * @return
	 * @throws Exception
	 * token 身份认证，里面包含了被调用者的身份
	 * timestamp 减轻dos攻击，利用时间差  客户端调用接口时对应的当前时间戳  作用二：请求超时
	 * nonce 6位随机数，可增加参数的不确定性
	 * sign 这个是利用自定义的规则，对参数做了处理，即时其他参数被截获或者伪造，这个值不易被伪造，他里面有标识用户身份的安全级别高的密钥ke'y
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String token = request.getHeader("token");
		String timestamp = request.getHeader("timestamp");
		// 随机字符串
		String nonce = request.getHeader("nonce");
		String sign = request.getHeader("sign");
		Assert.isTrue(!StringUtils.isEmpty(token) && !StringUtils.isEmpty(timestamp) && !StringUtils.isEmpty(sign), "参数错误");


		// 获取超时时间
		NotRepeatSubmit notRepeatSubmit = ApiUtil.getNotRepeatSubmit(handler);
		long expireTime = notRepeatSubmit == null ? 5 * 60 * 1000 : notRepeatSubmit.value();


		// 2. 请求时间间隔，这里逻辑不通，timestamp和这里的系统时间理论是相同的
		//如果一个请求参数再过了超时设置时间后还拿来请求调用接口，则回提示超时，请求失效了
		long reqeustInterval = System.currentTimeMillis() - Long.valueOf(timestamp);
		Assert.isTrue(reqeustInterval < expireTime, "请求超时，请重新请求");


		// 3. 校验Token是否存在
		ValueOperations<String, TokenInfo> tokenRedis = redisTemplate.opsForValue();
		TokenInfo tokenInfo = tokenRedis.get(token);
		Assert.notNull(tokenInfo, "token错误");


		// 4. 校验签名(将所有的参数加进来，防止别人篡改参数) 所有参数看参数名升续排序拼接成url
		// 请求参数 + token + timestamp + nonce
		String signString = ApiUtil.concatSignString(request) + tokenInfo.getAppInfo().getKey() + token + timestamp + nonce;
		String signature = MD5Util.encode(signString);
		boolean flag = signature.equals(sign);
		Assert.isTrue(flag, "签名错误");


		// 5. 拒绝重复调用(第一次访问时存储，过期时间和请求超时时间保持一致), 只有标注不允许重复提交注解的才会校验
		if (notRepeatSubmit != null) {
			ValueOperations<String, Integer> signRedis = redisTemplate.opsForValue();
			boolean exists = redisTemplate.hasKey(sign);
			Assert.isTrue(!exists, "请勿重复提交");
			signRedis.set(sign, 0, expireTime, TimeUnit.MILLISECONDS);
		}


		return super.preHandle(request, response, handler);
	}
}