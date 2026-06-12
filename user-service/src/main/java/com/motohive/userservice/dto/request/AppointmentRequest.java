package com.motohive.userservice.dto.request;

import com.motohive.userservice.enums.AppointmentType;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AppointmentRequest {
	
	@NotNull(message = "Vehicle ID is required")
	private Long vehicleId;
	
	@NotNull(message = "Listing ID is required")
	private Long listingId;
	// optional — null for guest bookings
	private Long userId;
	// required only if userId is null
	@Size(max = 100)
	private String guestName;
	
	@Pattern(regexp = "^[0-9]{10}$", message = "Phone must be 10 digits")
	private String guestPhone;
	
	@NotNull(message = "Appointment type is required")
	private AppointmentType appointmentType;
	
	@NotNull(message = "Preferred date is required")
	@Future(message = "Preferred date must be in the future")
	private LocalDate preferredDate;
	
	@NotNull(message = "Preferred time is required")
	private LocalTime preferredTime;
	
	@Size(max = 1000)
	private String notes;
	
}