package com.motohive.vehicleservice.dto.response;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VehicleImageResponse {
	
	private Long id;
	private String imageUrl;
	private Boolean isPrimary;
	private Byte displayOrder;
	private LocalDateTime uploadedAt;
	
}