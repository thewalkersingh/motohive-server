// exception/InvalidCredentialsException.java
package com.motohive.userservice.exception;

public class InvalidCredentialsException extends RuntimeException {
	
	public InvalidCredentialsException() {
		super("Invalid email or password");
	}
	
}