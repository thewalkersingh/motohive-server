package com.motohive.listingservice.repository;

import com.motohive.listingservice.entity.ListingEnquiry;
import com.motohive.listingservice.enums.EnquiryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListingEnquiryRepository extends JpaRepository<ListingEnquiry, Long> {
	
	List<ListingEnquiry> findByListingIdOrderByCreatedAtDesc(Long listingId);
	
	List<ListingEnquiry> findByBuyerIdOrderByCreatedAtDesc(Long buyerId);
	
	List<ListingEnquiry> findByListingIdAndStatus(Long listingId, EnquiryStatus status);
	
	int countByListingId(Long listingId);
	
	boolean existsByListingIdAndBuyerId(Long listingId, Long buyerId);
	
}