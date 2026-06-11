package com.motohive.listingservice.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateEnquiryRequest {
	
	@NotNull(message = "Buyer ID is required")
	private Long buyerId;
	
	@NotNull(message = "Listing ID is required")
	private Long listingId;
	
	@Size(max = 1000, message = "Message must not exceed 1000 characters")
	private String message;
	
	@Pattern(regexp = "^[0-9]{10,15}$", message = "Invalid contact number")
	private String contactNumber;
	
}