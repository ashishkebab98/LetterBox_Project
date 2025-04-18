package com.letterbox.user_ratings_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class UserRatingsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserRatingsServiceApplication.class, args);
	}
}
