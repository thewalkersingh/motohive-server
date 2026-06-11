package com.motohive.listingservice.dto.response;

import com.motohive.listingservice.enums.ListingStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ListingSummaryResponse {
	
	private Long id;
	private Long vehicleId;
	private Long sellerId;
	private BigDecimal price;
	private Boolean isNegotiable;
	private String meetingLocation;
	private ListingStatus status;
	private LocalDateTime listedAt;
	// brief vehicle info only
	private String vehicleBrand;
	private String vehicleModel;
	private Short vehicleYear;
	private String vehicleType;
	private String city;
	
}