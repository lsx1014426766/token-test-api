package com.ultimate.service.impl;

import com.balala.Application;
import com.balala.config.redis.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

//import com.atzy.wm.service.config.ServiceContentConfig;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {ServiceContentConfig.class})
@SpringBootTest(classes = Application.class)
public class RedisTest {
    @Resource
    private RedisUtil redisUtil;
    //LoggerFactory is not a Logback LoggerContext but Logback is on the classpath. Either remove Logback or the competing implementation (class org.slf4j.impl.Log4jLoggerFactory loaded from file:/D:/placeMavenRepository/org/slf4j/slf4j-log4j12/1.7.26/slf4j-log4j12-1.7.26.jar). If you are using WebLogic you will need to add 'org.slf4j' to prefer-application-packages in WEB-INF/weblogic.xml: org.slf4j.impl.Log4jLoggerFactory
    //https://www.cnblogs.com/passedbylove/p/9208682.html
    @Test
    public void test() {
        ArrayList<Integer> aids = new ArrayList<>();
        System.out.println(redisUtil.get("aaa"));


    }

    public static void main(String[] args) {
        LocalDateTime today_end = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        System.out.println(today_end);
        Date d = Date.from(today_end.atZone(ZoneId.systemDefault()).toInstant());
        System.out.println(d);
        Calendar calendar = Calendar.getInstance();
    }
}