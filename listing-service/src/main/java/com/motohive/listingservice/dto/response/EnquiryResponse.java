package com.motohive.listingservice.dto.response;

import com.motohive.listingservice.enums.EnquiryStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EnquiryResponse {
	
	private Long id;
	private Long listingId;
	private Long buyerId;
	private String message;
	private String contactNumber;
	private EnquiryStatus status;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
}