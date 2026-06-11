package com.motohive.listingservice.exception;

public class DuplicateActiveListingException extends RuntimeException {
	
	public DuplicateActiveListingException(Long vehicleId) {
		super("An active listing already exists for vehicle id: " + vehicleId);
	}
	
}