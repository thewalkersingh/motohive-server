package com.motohive.vehicleservice.entity;
import com.motohive.vehicleservice.enums.BikeType;
import com.motohive.vehicleservice.enums.TyreType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bike_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BikeDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vehicle_id", nullable = false, unique = true)
	private Vehicle vehicle;
	
	@Column(name = "engine_cc")
	private Integer engineCc;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "bike_type", length = 10)
	private BikeType bikeType;
	
	@Column
	private Boolean abs;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tyre_type", length = 10)
	private TyreType tyreType;
	
}