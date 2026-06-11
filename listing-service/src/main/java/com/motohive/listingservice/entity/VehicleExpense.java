package com.motohive.listingservice.entity;

import com.motohive.listingservice.enums.ExpenseType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "vehicle_expenses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleExpense {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	// ── Cross-service ref ─────────────────────────────
	@Column(name = "vehicle_id", nullable = false)
	private Long vehicleId;
	
	@Column(name = "seller_id", nullable = false)
	private Long sellerId;
	// ── Nullable — only set for listing-related expenses (e.g. PLATFORM_FEE) ──
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "listing_id")
	private Listing listing;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "expense_type", nullable = false, length = 20)
	private ExpenseType expenseType;
	
	@Column(nullable = false, precision = 12, scale = 2)
	private BigDecimal amount;
	
	@Column(length = 255)
	private String description;
	
	@Column(name = "incurred_at", nullable = false)
	private LocalDate incurredAt;
	
	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;
	
}