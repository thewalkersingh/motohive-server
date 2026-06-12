package com.motohive.userservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AddressRequest {
	
	@NotBlank(message = "Address line is required")
	@Size(max = 255)
	private String addressLine;
	
	@NotBlank(message = "City is required")
	@Size(max = 100)
	private String city;
	
	@NotBlank(message = "State is required")
	@Size(max = 100)
	private String state;
	
	@NotBlank(message = "Pincode is required")
	@Pattern(regexp = "^[0-9]{6}$", message = "Pincode must be 6 digits")
	private String pincode;
	private Boolean isDefault = false;
	
}