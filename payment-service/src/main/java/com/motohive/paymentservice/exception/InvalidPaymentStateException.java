// exception/InvalidPaymentStateException.java
package com.motohive.paymentservice.exception;

public class InvalidPaymentStateException extends RuntimeException {
	
	public InvalidPaymentStateException(String message) {
		super(message);
	}
	
}