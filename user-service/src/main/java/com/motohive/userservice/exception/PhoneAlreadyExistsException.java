// exception/PhoneAlreadyExistsException.java
package com.motohive.userservice.exception;

public class PhoneAlreadyExistsException extends RuntimeException {
	
	public PhoneAlreadyExistsException(String phone) {
		super("Phone already registered: " + phone);
	}
	
}