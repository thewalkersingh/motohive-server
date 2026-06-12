package com.motohive.paymentservice.entity;

import com.motohive.paymentservice.enums.OutboxStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment_outbox")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentOutbox {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "payment_id", nullable = false)
	private Long paymentId;
	
	@Column(name = "event_type", nullable = false, length = 50)
	private String eventType;
	
	@Column(nullable = false, columnDefinition = "JSON")
	private String payload;               // serialized PaymentCompletedEvent
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 15)
	@Builder.Default
	private OutboxStatus status = OutboxStatus.PENDING;
	
	@Column(name = "retry_count", nullable = false)
	@Builder.Default
	private Byte retryCount = 0;
	
	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;
	
	@Column(name = "published_at")
	private LocalDateTime publishedAt;
	
}