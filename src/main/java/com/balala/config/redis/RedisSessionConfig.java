package com.balala.config.redis;

import com.balala.utils.PropertyFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration;

//https://blog.csdn.net/sz85850597/article/details/82227490
@Slf4j
@Configuration
public class RedisSessionConfig extends RedisHttpSessionConfiguration{

    @Bean
    public static ConfigureRedisAction configureRedisAction()
    {
        return ConfigureRedisAction.NO_OP;
    }
    public RedisSessionConfig() {
        super();
        String ts= PropertyFile.getProperties().getProperty("redis.maxInactiveIntervalInSeconds");
        super.setMaxInactiveIntervalInSeconds(Integer.valueOf(ts));

    }

   /*@Primary
    @Bean
    @Qualifier("aa")
    public RedisOperationsSessionRepository sessionRepository(RedisOperationsSessionRepository rs){
        rs.setDefaultMaxInactiveInterval(maxInactiveSeconds);
        log.info("lsx test22 maxinactive seconds="+maxInactiveSeconds);
        return rs;
    }
*/

}
