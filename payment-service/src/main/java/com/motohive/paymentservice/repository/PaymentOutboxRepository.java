package com.motohive.paymentservice.repository;

import com.motohive.paymentservice.entity.PaymentOutbox;
import com.motohive.paymentservice.enums.OutboxStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentOutboxRepository extends JpaRepository<PaymentOutbox, Long> {
	
	List<PaymentOutbox> findByStatusOrderByCreatedAtAsc(OutboxStatus status);
	
	List<PaymentOutbox> findByStatusAndRetryCountLessThan(OutboxStatus status, Byte maxRetries);
	
}