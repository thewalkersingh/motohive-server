package com.motohive.vehicleservice.dto.response;
import com.motohive.vehicleservice.enums.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class VehicleResponse {
	
	// ── Vehicle core ─────────────────────────────────
	private Long id;
	private Long sellerId;
	private VehicleType vehicleType;
	private String brand;
	private String model;
	private Short year;
	private FuelType fuelType;
	private Transmission transmission;
	private BigDecimal mileageKmpl;
	private Integer odometerKm;
	private String color;
	private VehicleCondition condition;
	private String registrationNumber;
	private LocalDate insuranceExpiry;
	private Byte numberOfOwners;
	private String city;
	private VehicleStatus status;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	// ── Car-specific (null if not CAR) ────────────────
	private Integer engineCc;
	private Byte seatingCapacity;
	private Integer bootSpaceLitres;
	private Byte airbags;
	private DriveType driveType;
	private Boolean sunroof;
	private Boolean abs;
	
	// ── Bike-specific (null if not BIKE) ──────────────
	private BikeType bikeType;
	private TyreType tyreType;
	
	// ── EV-specific (null if not EV) ──────────────────
	private BigDecimal batteryCapacityKwh;
	private Integer rangeKm;
	private BigDecimal chargeTimeHours;
	private ConnectorType connectorType;
	private Integer topSpeedKmph;
	
	// ── Images ────────────────────────────────────────
	private List<VehicleImageResponse> images;
	
}