package com.motohive.paymentservice.service;

import com.motohive.paymentservice.dto.request.InitiatePaymentRequest;
import com.motohive.paymentservice.dto.request.ProcessPaymentRequest;
import com.motohive.paymentservice.dto.response.PaymentResponse;
import com.motohive.paymentservice.dto.response.PaymentSummaryResponse;

import java.util.List;

public interface PaymentService {
	
	PaymentResponse initiatePayment(InitiatePaymentRequest request);
	
	PaymentResponse processPayment(ProcessPaymentRequest request);
	
	PaymentResponse getPaymentById(Long id);
	
	PaymentResponse refundPayment(Long id);
	
	List<PaymentSummaryResponse> getPaymentsByBuyer(Long buyerId);
	
	List<PaymentSummaryResponse> getPaymentsBySeller(Long sellerId);
	
}