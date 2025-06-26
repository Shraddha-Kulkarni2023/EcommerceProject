package com.example.EcommerceProject.UserMicroService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.EcommerceProject.UserMicroService.Repository")
@ComponentScan(basePackages = "com.example.EcommerceProject.UserMicroService")
public class EcommerceProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceProjectApplication.class, args);
	}

}
