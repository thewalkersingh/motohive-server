package com.motohive.listingservice.dto.request;

import com.motohive.listingservice.enums.ExpenseType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CreateExpenseRequest {
	
	@NotNull(message = "Vehicle ID is required")
	private Long vehicleId;
	
	@NotNull(message = "Seller ID is required")
	private Long sellerId;
	private Long listingId;   // optional
	
	@NotNull(message = "Expense type is required")
	private ExpenseType expenseType;
	
	@NotNull(message = "Amount is required")
	@DecimalMin(value = "0.01", message = "Amount must be greater than 0")
	private BigDecimal amount;
	
	@Size(max = 255, message = "Description must not exceed 255 characters")
	private String description;
	
	@NotNull(message = "Incurred date is required")
	private LocalDate incurredAt;
	
}