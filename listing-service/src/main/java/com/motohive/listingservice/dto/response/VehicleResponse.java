package com.motohive.listingservice.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class VehicleResponse {
	
	private Long id;
	private Long sellerId;
	private String vehicleType;
	private String brand;
	private String model;
	private Short year;
	private String fuelType;
	private String transmission;
	private BigDecimal mileageKmpl;
	private Integer odometerKm;
	private String color;
	private String condition;
	private String registrationNumber;
	private LocalDate insuranceExpiry;
	private Byte numberOfOwners;
	private String city;
	private String status;
	private LocalDateTime createdAt;
	
	// type-specific fields
	private Integer engineCc;
	private Byte seatingCapacity;
	private Integer bootSpaceLitres;
	private Byte airbags;
	private String driveType;
	private Boolean sunroof;
	private Boolean abs;
	private String bikeType;
	private String tyreType;
	private BigDecimal batteryCapacityKwh;
	private Integer rangeKm;
	private BigDecimal chargeTimeHours;
	private String connectorType;
	private Integer topSpeedKmph;
	
}