package com.zql;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
@MapperScan(value ={"com.zql.business.dao","com.zql.common.dao"})
public class ExtjsIntegrationApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ExtjsIntegrationApplication.class, args);
	}
}
