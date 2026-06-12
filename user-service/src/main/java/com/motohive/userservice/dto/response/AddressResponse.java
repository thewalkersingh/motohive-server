package com.motohive.userservice.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AddressResponse {
	
	private Long id;
	private String addressLine;
	private String city;
	private String state;
	private String pincode;
	private Boolean isDefault;
	private LocalDateTime createdAt;
	
}