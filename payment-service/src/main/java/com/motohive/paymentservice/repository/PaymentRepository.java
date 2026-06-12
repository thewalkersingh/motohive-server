package com.motohive.paymentservice.repository;

import com.motohive.paymentservice.entity.Payment;
import com.motohive.paymentservice.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
	
	List<Payment> findByBuyerIdOrderByCreatedAtDesc(Long buyerId);
	
	List<Payment> findBySellerIdOrderByCreatedAtDesc(Long sellerId);
	
	Optional<Payment> findByListingIdAndStatus(Long listingId, PaymentStatus status);
	
	Optional<Payment> findByTransactionId(String transactionId);
	
	boolean existsByListingIdAndStatus(Long listingId, PaymentStatus status);
	
}