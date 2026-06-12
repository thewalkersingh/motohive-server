package com.motohive.paymentservice.controller;

import com.motohive.paymentservice.dto.request.InitiatePaymentRequest;
import com.motohive.paymentservice.dto.request.ProcessPaymentRequest;
import com.motohive.paymentservice.dto.response.PaymentResponse;
import com.motohive.paymentservice.dto.response.PaymentSummaryResponse;
import com.motohive.paymentservice.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
@Tag(name = "Payments", description = "Payment initiation, processing and refunds")
public class PaymentController {
	
	private final PaymentService paymentService;
	
	@PostMapping("/initiate")
	@Operation(summary = "Initiate payment", description = "Creates a payment record for a listing")
	public ResponseEntity<PaymentResponse> initiatePayment(
			@Valid @RequestBody InitiatePaymentRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED)
				       .body(paymentService.initiatePayment(request));
	}
	
	@PostMapping("/process")
	@Operation(summary = "Process payment", description = "Mock gateway — set simulateSuccess true/false")
	public ResponseEntity<PaymentResponse> processPayment(
			@Valid @RequestBody ProcessPaymentRequest request) {
		return ResponseEntity.ok(paymentService.processPayment(request));
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Get payment by ID")
	public ResponseEntity<PaymentResponse> getPaymentById(@PathVariable Long id) {
		return ResponseEntity.ok(paymentService.getPaymentById(id));
	}
	
	@PostMapping("/{id}/refund")
	@Operation(summary = "Refund payment", description = "Refunds a SUCCESS payment")
	public ResponseEntity<PaymentResponse> refundPayment(@PathVariable Long id) {
		return ResponseEntity.ok(paymentService.refundPayment(id));
	}
	
	@GetMapping("/buyer/{buyerId}")
	@Operation(summary = "Get payments by buyer")
	public ResponseEntity<List<PaymentSummaryResponse>> getByBuyer(
			@PathVariable Long buyerId) {
		return ResponseEntity.ok(paymentService.getPaymentsByBuyer(buyerId));
	}
	
	@GetMapping("/seller/{sellerId}")
	@Operation(summary = "Get payments by seller")
	public ResponseEntity<List<PaymentSummaryResponse>> getBySeller(
			@PathVariable Long sellerId) {
		return ResponseEntity.ok(paymentService.getPaymentsBySeller(sellerId));
	}
	
}