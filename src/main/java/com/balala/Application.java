package com.balala;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.XADataSourceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude={ XADataSourceAutoConfiguration.class, DataSourceAutoConfiguration.class})
//@MapperScan("com.ultimate.dao")//不用再逐个指定@Mapper
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})直接在springboot里设置
@EnableTransactionManagement
public class Application {

	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
