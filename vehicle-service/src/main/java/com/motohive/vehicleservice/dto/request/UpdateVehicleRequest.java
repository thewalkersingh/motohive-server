package com.motohive.vehicleservice.dto.request;
import com.motohive.vehicleservice.enums.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class UpdateVehicleRequest {
	
	// All fields optional — only non-null fields will be applied
	
	@Size(max = 50, message = "Color must not exceed 50 characters")
	private String color;
	private VehicleCondition condition;
	private LocalDate insuranceExpiry;
	
	@DecimalMin(value = "0.0", inclusive = false, message = "Mileage must be greater than 0")
	private BigDecimal mileageKmpl;
	
	@Min(value = 0, message = "Odometer reading cannot be negative")
	private Integer odometerKm;
	
	@Size(max = 100, message = "City must not exceed 100 characters")
	private String city;
	private VehicleStatus status;
	
	@Min(value = 1, message = "Number of owners must be at least 1")
	@Max(value = 10, message = "Number of owners cannot exceed 10")
	private Byte numberOfOwners;
	private LocalDate insuranceExpiryDate;
	
}