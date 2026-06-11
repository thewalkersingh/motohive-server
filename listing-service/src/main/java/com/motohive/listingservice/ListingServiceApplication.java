package com.motohive.listingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ListingServiceApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ListingServiceApplication.class, args);
		start();
	}
	
	static void start() {
		System.out.println("***************************************************************");
		System.out.println("*****************-------------------------*********************");
		System.out.println("****************| Listing-Service Started |********************");
		System.out.println("*****************-------------------------*********************");
		System.out.println("***************************************************************");
		
	}
	
}