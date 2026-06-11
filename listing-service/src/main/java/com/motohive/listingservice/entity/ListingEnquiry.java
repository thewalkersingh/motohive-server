package com.motohive.listingservice.entity;

import com.motohive.listingservice.enums.EnquiryStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "listing_enquiries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListingEnquiry {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "listing_id", nullable = false)
	private Listing listing;
	
	@Column(name = "buyer_id", nullable = false)
	private Long buyerId;
	
	@Column(columnDefinition = "TEXT")
	private String message;
	
	@Column(name = "contact_number", length = 15)
	private String contactNumber;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 10)
	@Builder.Default
	private EnquiryStatus status = EnquiryStatus.PENDING;
	
	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
	
}