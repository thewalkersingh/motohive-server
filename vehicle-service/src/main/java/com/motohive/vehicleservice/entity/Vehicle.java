package com.motohive.vehicleservice.entity;
import com.motohive.vehicleservice.enums.*;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vehicles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	// ── Cross-service reference (no FK constraint) ──
	@Column(name = "seller_id", nullable = false)
	private Long sellerId;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "vehicle_type", nullable = false, length = 10)
	private VehicleType vehicleType;
	
	@Column(nullable = false, length = 100)
	private String brand;
	
	@Column(nullable = false, length = 100)
	private String model;
	
	@Column(nullable = false)
	private Short year;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "fuel_type", nullable = false, length = 10)
	private FuelType fuelType;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 15)
	private Transmission transmission;
	
	@Column(name = "mileage_kmpl", precision = 5, scale = 2)
	private java.math.BigDecimal mileageKmpl;
	
	@Column(name = "odometer_km")
	private Integer odometerKm;
	
	@Column(length = 50)
	private String color;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "vehicle_condition",nullable = false, length = 10)
	private VehicleCondition condition;
	
	@Column(name = "registration_number", nullable = false, unique = true, length = 20)
	private String registrationNumber;
	
	@Column(name = "insurance_expiry")
	private LocalDate insuranceExpiry;
	
	@Column(name = "number_of_owners", nullable = false)
	private Byte numberOfOwners;
	
	@Column(nullable = false, length = 100)
	private String city;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 10)
	@Builder.Default
	private VehicleStatus status = VehicleStatus.ACTIVE;
	
	// ── Relationships ────────────────────────────────
	
	@OneToOne(mappedBy = "vehicle", cascade = CascadeType.ALL)
	private CarDetails carDetails;
	
	@OneToOne(mappedBy = "vehicle", cascade = CascadeType.ALL)
	private BikeDetails bikeDetails;
	
	@OneToOne(mappedBy = "vehicle", cascade = CascadeType.ALL)
	private EvDetails evDetails;
	
	@OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Builder.Default
	private List<VehicleImage> images = new ArrayList<>();
	
	// ── Audit ────────────────────────────────────────
	
	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
	
}