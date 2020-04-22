package com.balala.config;

import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * 从外部配置文件中获取属性
 */
public class Property {
	
	private static PropertySourcesPlaceholderConfigurer placeholderConfigurer ;
	
	public static String getValue(String key){
		if(key==null||"".equals(key.trim()) ||placeholderConfigurer == null){
			return "";
		}		

		String value = (String) 
				placeholderConfigurer.getAppliedPropertySources().get(PropertySourcesPlaceholderConfigurer.LOCAL_PROPERTIES_PROPERTY_SOURCE_NAME).getProperty(key);
		
	
		value=value==null?"":value;
		return value;
	}


	public static void setPlaceholderConfigurer(
			PropertySourcesPlaceholderConfigurer placeholderConfigurer) {
		Property.placeholderConfigurer = placeholderConfigurer;
	}

	public static PropertySourcesPlaceholderConfigurer getPlaceholderConfigurer() {
		return placeholderConfigurer;
	}

}
