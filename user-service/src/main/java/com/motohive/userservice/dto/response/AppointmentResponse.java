package com.motohive.userservice.dto.response;

import com.motohive.userservice.enums.AppointmentStatus;
import com.motohive.userservice.enums.AppointmentType;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class AppointmentResponse {
	
	private Long id;
	private Long vehicleId;
	private Long listingId;
	private Long userId;
	private String guestName;
	private String guestPhone;
	private AppointmentType appointmentType;
	private LocalDate preferredDate;
	private LocalTime preferredTime;
	private String notes;
	private AppointmentStatus status;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
}