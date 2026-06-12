package com.motohive.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserServiceApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
		start();
	}
	static void start() {
		System.out.println("***************************************************************");
		System.out.println("*****************-------------------------*********************");
		System.out.println("******************| User-Service Started |*********************");
		System.out.println("*****************-------------------------*********************");
		System.out.println("***************************************************************");
		
	}
}