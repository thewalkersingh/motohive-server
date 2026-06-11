package com.motohive.vehicleservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VehicleServiceApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(VehicleServiceApplication.class, args);
		printmsg();
	}
	
	static void printmsg() {
		System.out.println("***************************************************************");
		System.out.println("*****************-------------------------*********************");
		System.out.println("****************| Vehicle Service Started |********************");
		System.out.println("*****************-------------------------*********************");
		System.out.println("***************************************************************");
		
	}
	
}