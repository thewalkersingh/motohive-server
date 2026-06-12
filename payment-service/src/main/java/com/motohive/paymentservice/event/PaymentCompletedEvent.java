package com.motohive.paymentservice.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCompletedEvent {
	
	private Long paymentId;
	private Long listingId;
	private Long buyerId;
	private Long sellerId;
	private BigDecimal listingPrice;
	private BigDecimal bookingAmount;
	private BigDecimal remainingAmount;
	private BigDecimal platformFeeAmount;
	private String transactionId;
	private LocalDateTime completedAt;
	
}