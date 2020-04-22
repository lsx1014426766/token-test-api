package com.balala.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 从外部配置文件中获取属性
 */
@Slf4j
public class PropertyFile {

    private static Properties pro;

    static {
        //本地测试则为classpath=resources
        //test环境，设置classpath=config/.
       // String path="config/application.properties";
        String path="application.properties";
        ClassLoader classLoader = PropertyFile.class.getClassLoader();
        InputStream inputStream =classLoader.getResourceAsStream(path);
        try {
            pro = new Properties();
            pro.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Properties getProperties() {
        return pro;
    }
}
