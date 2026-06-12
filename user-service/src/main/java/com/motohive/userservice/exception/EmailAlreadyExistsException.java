// exception/EmailAlreadyExistsException.java
package com.motohive.userservice.exception;

public class EmailAlreadyExistsException extends RuntimeException {
	
	public EmailAlreadyExistsException(String email) {
		super("Email already registered: " + email);
	}
	
}