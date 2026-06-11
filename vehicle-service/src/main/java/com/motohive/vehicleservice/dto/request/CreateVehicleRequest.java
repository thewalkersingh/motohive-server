package com.motohive.vehicleservice.dto.request;
import com.motohive.vehicleservice.enums.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CreateVehicleRequest {
	
	@NotNull(message = "Seller ID is required")
	private Long sellerId;
	
	@NotNull(message = "Vehicle type is required")
	private VehicleType vehicleType;
	
	@NotBlank(message = "Brand is required")
	@Size(max = 100, message = "Brand must not exceed 100 characters")
	private String brand;
	
	@NotBlank(message = "Model is required")
	@Size(max = 100, message = "Model must not exceed 100 characters")
	private String model;
	
	@NotNull(message = "Year is required")
	@Min(value = 1980, message = "Year must be 1980 or later")
	@Max(value = 2025, message = "Year cannot be in the future")
	private Short year;
	
	@NotNull(message = "Fuel type is required")
	private FuelType fuelType;
	
	@NotNull(message = "Transmission is required")
	private Transmission transmission;
	
	@DecimalMin(value = "0.0", inclusive = false, message = "Mileage must be greater than 0")
	private BigDecimal mileageKmpl;
	
	@Min(value = 0, message = "Odometer reading cannot be negative")
	private Integer odometerKm;
	
	@Size(max = 50, message = "Color must not exceed 50 characters")
	private String color;
	
	@NotNull(message = "Condition is required")
	private VehicleCondition condition;
	
	@NotBlank(message = "Registration number is required")
	@Size(max = 20, message = "Registration number must not exceed 20 characters")
	private String registrationNumber;
	private LocalDate insuranceExpiry;
	
	@NotNull(message = "Number of owners is required")
	@Min(value = 1, message = "Number of owners must be at least 1")
	@Max(value = 10, message = "Number of owners cannot exceed 10")
	private Byte numberOfOwners;
	
	@NotBlank(message = "City is required")
	@Size(max = 100, message = "City must not exceed 100 characters")
	private String city;
	
	// ── Type-specific details (only one will be set) ──
	
	@Valid  // triggers nested validation
	private CarDetailsRequest carDetails;
	
	@Valid
	private BikeDetailsRequest bikeDetails;
	
	@Valid
	private EvDetailsRequest evDetails;
	
}