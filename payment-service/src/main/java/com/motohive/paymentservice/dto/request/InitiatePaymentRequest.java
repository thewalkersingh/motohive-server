package com.motohive.paymentservice.dto.request;

import com.motohive.paymentservice.enums.BookingAmountType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class InitiatePaymentRequest {
	
	@NotNull(message = "Listing ID is required")
	private Long listingId;
	
	@NotNull(message = "Buyer ID is required")
	private Long buyerId;
	
	@NotNull(message = "Seller ID is required")
	private Long sellerId;
	
	@NotNull(message = "Listing price is required")
	@DecimalMin(value = "1.0", message = "Listing price must be greater than 0")
	private BigDecimal listingPrice;
	
	@NotNull(message = "Booking amount type is required")
	private BookingAmountType bookingAmountType;
	// flat amount if FIXED (e.g. 5000)
	// percentage value if PERCENTAGE (e.g. 5.0 means 5%)
	@NotNull(message = "Booking amount value is required")
	@DecimalMin(value = "0.01", message = "Booking amount value must be greater than 0")
	private BigDecimal bookingAmountValue;
	
}