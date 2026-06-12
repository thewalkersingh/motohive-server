package com.motohive.paymentservice.dto.response;

import com.motohive.paymentservice.enums.PaymentStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentSummaryResponse {
	
	private Long id;
	private Long listingId;
	private BigDecimal bookingAmount;
	private BigDecimal remainingAmount;
	private PaymentStatus status;
	private String transactionId;
	private LocalDateTime initiatedAt;
	
}