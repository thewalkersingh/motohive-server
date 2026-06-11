package com.motohive.vehicleservice.exception;
public class DuplicateRegistrationException extends RuntimeException {
	
	public DuplicateRegistrationException(String registrationNumber) {
		super("Vehicle already exists with registration number: " + registrationNumber);
	}
	
}