package com.motohive.listingservice.dto.response;

import com.motohive.listingservice.enums.ListingStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ListingResponse {
	
	// ── Listing fields ────────────────────────────────
	private Long id;
	private Long vehicleId;
	private Long sellerId;
	private BigDecimal price;
	private Boolean isNegotiable;
	private String description;
	private String meetingLocation;
	private ListingStatus status;
	private LocalDateTime listedAt;
	private LocalDateTime soldAt;
	private LocalDateTime expiresAt;
	private LocalDateTime updatedAt;
	// ── Vehicle details (from vehicle-service via Feign) ──
	private VehicleResponse vehicle;
	
	// note: expectedPrice intentionally excluded — private field
}