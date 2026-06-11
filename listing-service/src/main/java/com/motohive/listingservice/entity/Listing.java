package com.motohive.listingservice.entity;

import com.motohive.listingservice.enums.ListingStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "listings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Listing {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	// ── Cross-service refs (no DB FK) ─────────────────
	@Column(name = "vehicle_id", nullable = false)
	private Long vehicleId;
	
	@Column(name = "seller_id", nullable = false)
	private Long sellerId;
	// ── Pricing ───────────────────────────────────────
	@Column(nullable = false, precision = 12, scale = 2)
	private BigDecimal price;
	
	@Column(name = "expected_price", precision = 12, scale = 2)
	private BigDecimal expectedPrice;             // private — never exposed in response
	
	@Column(name = "final_sale_price", precision = 12, scale = 2)
	private BigDecimal finalSalePrice;            // filled by payment-service via Kafka
	// ── Listing details ───────────────────────────────
	@Column(name = "is_negotiable", nullable = false)
	@Builder.Default
	private Boolean isNegotiable = false;
	
	@Column(columnDefinition = "TEXT")
	private String description;
	
	@Column(name = "meeting_location", length = 255)
	private String meetingLocation;
	// ── Status & timestamps ───────────────────────────
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 10)
	@Builder.Default
	private ListingStatus status = ListingStatus.ACTIVE;
	
	@Column(name = "listed_at", updatable = false)
	private LocalDateTime listedAt;
	
	@Column(name = "sold_at")
	private LocalDateTime soldAt;
	
	@Column(name = "expires_at")
	private LocalDateTime expiresAt;
	
	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
	// ── Relationships ─────────────────────────────────
	@OneToMany(mappedBy = "listing", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Builder.Default
	private List<ListingEnquiry> enquiries = new ArrayList<>();
	
	@OneToMany(mappedBy = "listing", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Builder.Default
	private List<VehicleExpense> expenses = new ArrayList<>();
	
	@PrePersist
	protected void onCreate() {
		listedAt = LocalDateTime.now();
		if (expiresAt == null) {
			expiresAt = listedAt.plusDays(30);  // default 30-day expiry
		}
	}
	
}