package com.motohive.paymentservice.service.impl;

import com.motohive.paymentservice.dto.request.InitiatePaymentRequest;
import com.motohive.paymentservice.dto.request.ProcessPaymentRequest;
import com.motohive.paymentservice.dto.response.PaymentResponse;
import com.motohive.paymentservice.dto.response.PaymentSummaryResponse;
import com.motohive.paymentservice.entity.Payment;
import com.motohive.paymentservice.enums.BookingAmountType;
import com.motohive.paymentservice.enums.PaymentStatus;
import com.motohive.paymentservice.event.PaymentCompletedEvent;
import com.motohive.paymentservice.exception.InvalidPaymentStateException;
import com.motohive.paymentservice.exception.PaymentNotFoundException;
import com.motohive.paymentservice.kafka.PaymentEventPublisher;
import com.motohive.paymentservice.mapper.PaymentMapper;
import com.motohive.paymentservice.repository.PaymentRepository;
import com.motohive.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {
	
	private final PaymentRepository paymentRepository;
	private final PaymentMapper paymentMapper;
	private final PaymentEventPublisher eventPublisher;
	
	@Value("${motohive.payment.platform-fee-percent:2.50}")
	private BigDecimal platformFeePercent;
	
	// ── Initiate ─────────────────────────────────────
	
	@Override
	@Transactional
	public PaymentResponse initiatePayment(InitiatePaymentRequest request) {
		log.info("Initiating payment for listing: {}", request.getListingId());
		
		// block duplicate active payment for same listing
		if (paymentRepository.existsByListingIdAndStatus(
				request.getListingId(), PaymentStatus.SUCCESS)) {
			throw new InvalidPaymentStateException(
					"Listing " + request.getListingId() + " is already paid");
		}
		
		// calculate booking amount
		BigDecimal bookingAmount = calculateBookingAmount(
				request.getListingPrice(),
				request.getBookingAmountType(),
				request.getBookingAmountValue()
		);
		
		BigDecimal remainingAmount = request.getListingPrice()
				                             .subtract(bookingAmount)
				                             .setScale(2, RoundingMode.HALF_UP);
		
		BigDecimal platformFeeAmount = request.getListingPrice()
				                               .multiply(platformFeePercent)
				                               .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
		
		Payment payment = Payment.builder()
				                  .listingId(request.getListingId())
				                  .buyerId(request.getBuyerId())
				                  .sellerId(request.getSellerId())
				                  .listingPrice(request.getListingPrice())
				                  .bookingAmountType(request.getBookingAmountType())
				                  .bookingAmountValue(request.getBookingAmountValue())
				                  .bookingAmount(bookingAmount)
				                  .remainingAmount(remainingAmount)
				                  .platformFeePercent(platformFeePercent)
				                  .platformFeeAmount(platformFeeAmount)
				                  .status(PaymentStatus.INITIATED)
				                  .build();
		
		payment = paymentRepository.save(payment);
		log.info("Payment initiated with id: {}", payment.getId());
		return paymentMapper.toResponse(payment);
	}
	
	// ── Process (Mock Gateway) ────────────────────────
	
	@Override
	@Transactional
	public PaymentResponse processPayment(ProcessPaymentRequest request) {
		log.info("Processing payment id: {}", request.getPaymentId());
		
		Payment payment = findPaymentById(request.getPaymentId());
		
		// only INITIATED payments can be processed
		if (payment.getStatus() != PaymentStatus.INITIATED) {
			throw new InvalidPaymentStateException(
					"Payment " + payment.getId() + " is already " + payment.getStatus());
		}
		
		// move to PROCESSING
		payment.setStatus(PaymentStatus.PROCESSING);
		paymentRepository.save(payment);
		
		// simulate mock gateway response
		if (Boolean.TRUE.equals(request.getSimulateSuccess())) {
			payment.setStatus(PaymentStatus.SUCCESS);
			payment.setTransactionId("TXN-" + UUID.randomUUID().toString()
					                                  .substring(0, 8).toUpperCase());
			payment.setCompletedAt(LocalDateTime.now());
			payment = paymentRepository.save(payment);
			
			// save to outbox — same transaction guarantees atomicity
			PaymentCompletedEvent event = buildCompletedEvent(payment);
			eventPublisher.saveToOutbox(payment, event);
			
			log.info("Payment {} succeeded, transaction: {}",
					payment.getId(), payment.getTransactionId());
		} else {
			payment.setStatus(PaymentStatus.FAILED);
			payment.setFailureReason("Mock gateway: payment declined");
			payment.setCompletedAt(LocalDateTime.now());
			payment = paymentRepository.save(payment);
			log.info("Payment {} failed (simulated)", payment.getId());
		}
		
		return paymentMapper.toResponse(payment);
	}
	
	// ── Read ─────────────────────────────────────────
	
	@Override
	@Transactional(readOnly = true)
	public PaymentResponse getPaymentById(Long id) {
		return paymentMapper.toResponse(findPaymentById(id));
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<PaymentSummaryResponse> getPaymentsByBuyer(Long buyerId) {
		return paymentRepository.findByBuyerIdOrderByCreatedAtDesc(buyerId)
				       .stream().map(paymentMapper::toSummaryResponse).toList();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<PaymentSummaryResponse> getPaymentsBySeller(Long sellerId) {
		return paymentRepository.findBySellerIdOrderByCreatedAtDesc(sellerId)
				       .stream().map(paymentMapper::toSummaryResponse).toList();
	}
	
	// ── Refund ────────────────────────────────────────
	
	@Override
	@Transactional
	public PaymentResponse refundPayment(Long id) {
		Payment payment = findPaymentById(id);
		
		if (payment.getStatus() != PaymentStatus.SUCCESS) {
			throw new InvalidPaymentStateException(
					"Only SUCCESS payments can be refunded");
		}
		
		payment.setStatus(PaymentStatus.REFUNDED);
		payment.setCompletedAt(LocalDateTime.now());
		payment = paymentRepository.save(payment);
		
		log.info("Payment {} refunded", payment.getId());
		return paymentMapper.toResponse(payment);
	}
	
	// ── Private helpers ───────────────────────────────
	
	private Payment findPaymentById(Long id) {
		return paymentRepository.findById(id)
				       .orElseThrow(() -> new PaymentNotFoundException(id));
	}
	
	private BigDecimal calculateBookingAmount(BigDecimal listingPrice,
			BookingAmountType type,
			BigDecimal value) {
		if (type == BookingAmountType.FIXED) {
			return value.setScale(2, RoundingMode.HALF_UP);
		} else {
			// PERCENTAGE
			return listingPrice.multiply(value)
					       .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
		}
	}
	
	private PaymentCompletedEvent buildCompletedEvent(Payment payment) {
		return PaymentCompletedEvent.builder()
				       .paymentId(payment.getId())
				       .listingId(payment.getListingId())
				       .buyerId(payment.getBuyerId())
				       .sellerId(payment.getSellerId())
				       .listingPrice(payment.getListingPrice())
				       .bookingAmount(payment.getBookingAmount())
				       .remainingAmount(payment.getRemainingAmount())
				       .platformFeeAmount(payment.getPlatformFeeAmount())
				       .transactionId(payment.getTransactionId())
				       .completedAt(payment.getCompletedAt())
				       .build();
	}
	
}