package com.motohive.paymentservice.dto.response;

import com.motohive.paymentservice.enums.BookingAmountType;
import com.motohive.paymentservice.enums.PaymentMethod;
import com.motohive.paymentservice.enums.PaymentStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentResponse {
	
	private Long id;
	private Long listingId;
	private Long buyerId;
	private Long sellerId;
	private BigDecimal listingPrice;
	private BookingAmountType bookingAmountType;
	private BigDecimal bookingAmountValue;
	private BigDecimal bookingAmount;
	private BigDecimal remainingAmount;
	private BigDecimal platformFeePercent;
	private BigDecimal platformFeeAmount;
	private PaymentMethod paymentMethod;
	private String transactionId;
	private PaymentStatus status;
	private String failureReason;
	private LocalDateTime initiatedAt;
	private LocalDateTime completedAt;
	private LocalDateTime createdAt;
	
}