package com.motohive.vehicleservice.entity;
import com.motohive.vehicleservice.enums.DriveType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "car_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	// ── OneToOne back-reference ──────────────────────
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vehicle_id", nullable = false, unique = true)
	private Vehicle vehicle;
	
	@Column(name = "engine_cc")
	private Integer engineCc;
	
	@Column(name = "seating_capacity")
	private Byte seatingCapacity;
	
	@Column(name = "boot_space_litres")
	private Integer bootSpaceLitres;
	
	@Column
	private Byte airbags;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "drive_type", length = 5)
	private DriveType driveType;
	
	@Column
	private Boolean sunroof;
	
	@Column
	private Boolean abs;
	
}