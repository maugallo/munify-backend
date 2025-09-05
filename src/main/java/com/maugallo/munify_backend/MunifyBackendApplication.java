package com.maugallo.munify_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan(basePackages = "com.maugallo.munify_backend")
public class MunifyBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(MunifyBackendApplication.class, args);
	}

}
