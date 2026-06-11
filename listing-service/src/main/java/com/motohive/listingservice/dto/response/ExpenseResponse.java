package com.motohive.listingservice.dto.response;

import com.motohive.listingservice.enums.ExpenseType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ExpenseResponse {
	
	private Long id;
	private Long vehicleId;
	private Long sellerId;
	private Long listingId;
	private ExpenseType expenseType;
	private BigDecimal amount;
	private String description;
	private LocalDate incurredAt;
	private LocalDateTime createdAt;
	
}