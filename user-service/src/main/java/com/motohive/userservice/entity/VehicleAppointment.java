package com.motohive.userservice.entity;

import com.motohive.userservice.enums.AppointmentStatus;
import com.motohive.userservice.enums.AppointmentType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "vehicle_appointments")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class VehicleAppointment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	// ── Cross-service refs ────────────────────────────
	@Column(name = "vehicle_id", nullable = false)
	private Long vehicleId;
	
	@Column(name = "listing_id", nullable = false)
	private Long listingId;
	
	// ── nullable — null if guest booking ─────────────
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	// ── Guest fields (used when user is not registered) ──
	@Column(name = "guest_name", length = 100)
	private String guestName;
	
	@Column(name = "guest_phone", length = 15)
	private String guestPhone;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "appointment_type", nullable = false, length = 15)
	private AppointmentType appointmentType;
	
	@Column(name = "preferred_date", nullable = false)
	private LocalDate preferredDate;
	
	@Column(name = "preferred_time", nullable = false)
	private LocalTime preferredTime;
	
	@Column(columnDefinition = "TEXT")
	private String notes;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 15)
	@Builder.Default
	private AppointmentStatus status = AppointmentStatus.PENDING;
	
	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
}