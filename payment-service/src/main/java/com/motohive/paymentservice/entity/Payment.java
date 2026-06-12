package com.motohive.paymentservice.entity;

import com.motohive.paymentservice.enums.BookingAmountType;
import com.motohive.paymentservice.enums.PaymentMethod;
import com.motohive.paymentservice.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	// ── Cross-service refs ────────────────────────────
	@Column(name = "listing_id", nullable = false)
	private Long listingId;
	
	@Column(name = "buyer_id", nullable = false)
	private Long buyerId;
	
	@Column(name = "seller_id", nullable = false)
	private Long sellerId;
	// ── Pricing ───────────────────────────────────────
	@Column(name = "listing_price", nullable = false, precision = 12, scale = 2)
	private BigDecimal listingPrice;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "booking_amount_type", nullable = false, length = 15)
	private BookingAmountType bookingAmountType;
	
	@Column(name = "booking_amount_value", nullable = false, precision = 10, scale = 2)
	private BigDecimal bookingAmountValue;   // flat amount OR percentage value
	
	@Column(name = "booking_amount", nullable = false, precision = 12, scale = 2)
	private BigDecimal bookingAmount;        // calculated
	
	@Column(name = "remaining_amount", nullable = false, precision = 12, scale = 2)
	private BigDecimal remainingAmount;      // listingPrice - bookingAmount
	
	@Column(name = "platform_fee_percent", nullable = false, precision = 5, scale = 2)
	private BigDecimal platformFeePercent;
	
	@Column(name = "platform_fee_amount", nullable = false, precision = 12, scale = 2)
	private BigDecimal platformFeeAmount;    // calculated
	// ── Gateway ───────────────────────────────────────
	@Enumerated(EnumType.STRING)
	@Column(name = "payment_method", nullable = false, length = 15)
	@Builder.Default
	private PaymentMethod paymentMethod = PaymentMethod.MOCK;
	
	@Column(name = "transaction_id", unique = true, length = 100)
	private String transactionId;
	// ── Status ────────────────────────────────────────
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 15)
	@Builder.Default
	private PaymentStatus status = PaymentStatus.INITIATED;
	
	@Column(name = "failure_reason", length = 255)
	private String failureReason;
	// ── Timestamps ────────────────────────────────────
	@Column(name = "initiated_at", updatable = false)
	private LocalDateTime initiatedAt;
	
	@Column(name = "completed_at")
	private LocalDateTime completedAt;
	
	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
	
	@PrePersist
	protected void onCreate() {
		initiatedAt = LocalDateTime.now();
	}
	
}