package com.motohive.paymentservice.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.motohive.paymentservice.entity.Payment;
import com.motohive.paymentservice.entity.PaymentOutbox;
import com.motohive.paymentservice.enums.OutboxStatus;
import com.motohive.paymentservice.event.PaymentCompletedEvent;
import com.motohive.paymentservice.repository.PaymentOutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentEventPublisher {
	
	private final KafkaTemplate<String, String> kafkaTemplate;
	private final PaymentOutboxRepository outboxRepository;
	private final ObjectMapper objectMapper;
	public static final String PAYMENT_COMPLETED_TOPIC = "payment.completed";
	
	// ── Save event to outbox (called inside payment @Transactional) ──
	public void saveToOutbox(Payment payment, PaymentCompletedEvent event) {
		try {
			String payload = objectMapper.writeValueAsString(event);
			PaymentOutbox outbox = PaymentOutbox.builder()
					                       .paymentId(payment.getId())
					                       .eventType("PAYMENT_SUCCESS")
					                       .payload(payload)
					                       .status(OutboxStatus.PENDING)
					                       .build();
			outboxRepository.save(outbox);
			log.info("Saved outbox entry for payment id: {}", payment.getId());
		} catch (Exception e) {
			log.error("Failed to save outbox for payment {}: {}", payment.getId(), e.getMessage());
			throw new RuntimeException("Failed to save payment outbox", e);
		}
	}
	
	// ── Publish a single outbox entry to Kafka ────────
	public void publishOutboxEntry(PaymentOutbox outbox) {
		kafkaTemplate.send(PAYMENT_COMPLETED_TOPIC,
				String.valueOf(outbox.getPaymentId()),
				outbox.getPayload());
	}
	
}