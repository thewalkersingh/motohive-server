package com.motohive.listingservice.dto.request;

import com.motohive.listingservice.enums.ListingStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateListingRequest {
	
	@DecimalMin(value = "1.0", message = "Price must be greater than 0")
	private BigDecimal price;
	
	@DecimalMin(value = "1.0", message = "Expected price must be greater than 0")
	private BigDecimal expectedPrice;
	private Boolean isNegotiable;
	
	@Size(max = 2000, message = "Description must not exceed 2000 characters")
	private String description;
	
	@Size(max = 255, message = "Meeting location must not exceed 255 characters")
	private String meetingLocation;
	private ListingStatus status;
	
}