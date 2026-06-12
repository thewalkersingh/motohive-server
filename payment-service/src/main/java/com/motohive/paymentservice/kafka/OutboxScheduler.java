package com.motohive.paymentservice.kafka;

import com.motohive.paymentservice.entity.PaymentOutbox;
import com.motohive.paymentservice.enums.OutboxStatus;
import com.motohive.paymentservice.repository.PaymentOutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class OutboxScheduler {
	
	private final PaymentOutboxRepository outboxRepository;
	private final PaymentEventPublisher eventPublisher;
	private static final byte MAX_RETRIES = 5;
	
	// runs every 5 seconds
	@Scheduled(fixedDelay = 5000)
	@Transactional
	public void processOutbox() {
		List<PaymentOutbox> pendingEvents = outboxRepository
				                                    .findByStatusAndRetryCountLessThan(OutboxStatus.PENDING, MAX_RETRIES);
		
		if (pendingEvents.isEmpty()) return;
		
		log.info("Processing {} pending outbox entries", pendingEvents.size());
		
		for (PaymentOutbox outbox : pendingEvents) {
			try {
				eventPublisher.publishOutboxEntry(outbox);
				outbox.setStatus(OutboxStatus.PUBLISHED);
				outbox.setPublishedAt(LocalDateTime.now());
				log.info("Published outbox entry id: {} for payment: {}",
						outbox.getId(), outbox.getPaymentId());
			} catch (Exception e) {
				outbox.setRetryCount((byte) (outbox.getRetryCount() + 1));
				if (outbox.getRetryCount() >= MAX_RETRIES) {
					outbox.setStatus(OutboxStatus.FAILED);
					log.error("Outbox entry {} permanently failed after {} retries",
							outbox.getId(), MAX_RETRIES);
				} else {
					log.warn("Outbox entry {} failed, retry {}/{}",
							outbox.getId(), outbox.getRetryCount(), MAX_RETRIES);
				}
			}
			outboxRepository.save(outbox);
		}
	}
	
}