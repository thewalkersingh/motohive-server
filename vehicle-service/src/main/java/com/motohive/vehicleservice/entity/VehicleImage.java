package com.motohive.vehicleservice.entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "vehicle_images")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleImage {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vehicle_id", nullable = false)
	private Vehicle vehicle;
	
	@Column(name = "image_url", nullable = false, length = 500)
	private String imageUrl;
	
	@Column(name = "is_primary", nullable = false)
	@Builder.Default
	private Boolean isPrimary = false;
	
	@Column(name = "display_order")
	private Byte displayOrder;
	
	@CreationTimestamp
	@Column(name = "uploaded_at", updatable = false)
	private LocalDateTime uploadedAt;
	
}