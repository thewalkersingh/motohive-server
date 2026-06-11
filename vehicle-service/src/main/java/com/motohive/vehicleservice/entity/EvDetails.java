package com.motohive.vehicleservice.entity;
import com.motohive.vehicleservice.enums.ConnectorType;
import com.motohive.vehicleservice.enums.DriveType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "ev_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vehicle_id", nullable = false, unique = true)
	private Vehicle vehicle;
	
	@Column(name = "battery_capacity_kwh", precision = 5, scale = 2)
	private BigDecimal batteryCapacityKwh;
	
	@Column(name = "range_km")
	private Integer rangeKm;
	
	@Column(name = "charge_time_hours", precision = 4, scale = 2)
	private BigDecimal chargeTimeHours;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "connector_type", length = 10)
	private ConnectorType connectorType;
	
	@Column(name = "top_speed_kmph")
	private Integer topSpeedKmph;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "drive_type", length = 5)
	private DriveType driveType;
	
}