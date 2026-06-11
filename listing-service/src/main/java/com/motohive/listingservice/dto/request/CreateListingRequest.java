package com.motohive.listingservice.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateListingRequest {
	
	@NotNull(message = "Vehicle ID is required")
	private Long vehicleId;
	
	@NotNull(message = "Seller ID is required")
	private Long sellerId;
	
	@NotNull(message = "Price is required")
	@DecimalMin(value = "1.0", message = "Price must be greater than 0")
	private BigDecimal price;
	
	@DecimalMin(value = "1.0", message = "Expected price must be greater than 0")
	private BigDecimal expectedPrice;
	private Boolean isNegotiable = false;
	
	@Size(max = 2000, message = "Description must not exceed 2000 characters")
	private String description;
	
	@Size(max = 255, message = "Meeting location must not exceed 255 characters")
	private String meetingLocation;
	
}