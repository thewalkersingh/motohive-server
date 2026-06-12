package com.motohive.paymentservice.mapper;

import com.motohive.paymentservice.dto.response.PaymentResponse;
import com.motohive.paymentservice.dto.response.PaymentSummaryResponse;
import com.motohive.paymentservice.entity.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
	
	PaymentResponse toResponse(Payment payment);
	
	PaymentSummaryResponse toSummaryResponse(Payment payment);
	
}