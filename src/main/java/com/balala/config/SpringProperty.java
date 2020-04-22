package com.balala.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.*;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Properties;

/**
 * @Author: lsx  从env中获取属性
 * @Date: 2019/9/27
 * @Description
 */
@Component
public class SpringProperty implements ApplicationContextAware {
    private static ApplicationContext applicationContext;
    /** 配置文件中原始属性带占位符*/
    private static Properties properties = new Properties();
    private static PropertySourcesPropertyResolver propertySourcesPropertyResolver = null;

    /** 获取上下文属性*/
    private static void init() {
        try {
            Environment env = applicationContext.getEnvironment();
            StandardEnvironment standardServletEnvironment = (StandardEnvironment) env;
            MutablePropertySources mutablePropertySources = standardServletEnvironment.getPropertySources();
            Iterator<PropertySource<?>> iterator =  mutablePropertySources.iterator();
            while (iterator.hasNext()) {
                PropertySource propertySource = iterator.next();
                Object source =  propertySource.getSource();
                if (source instanceof Properties) {
                    Properties prop = (Properties) propertySource.getSource();
                    properties.putAll(prop);
                }
            }
            propertySourcesPropertyResolver = new PropertySourcesPropertyResolver(standardServletEnvironment.getPropertySources());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取属性文件中原始属性值,可能含有变量
     * @param propertyName
     * @return
     */
    public static String getPropertyRaw(String propertyName) {
        return properties.getProperty(propertyName);
    }

    /**
     * 生效属性真实值
     * @param key
     * @return
     */
    public static String getProperty(String key) {
        return  propertySourcesPropertyResolver.getProperty(key);
    }

    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        applicationContext = arg0;
        init();
    }
}
