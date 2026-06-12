package com.motohive.paymentservice.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProcessPaymentRequest {
	
	@NotNull(message = "Payment ID is required")
	private Long paymentId;
	// simulate success or failure in mock mode
	@NotNull(message = "Simulate success flag is required")
	private Boolean simulateSuccess;
	
}