package com.motohive.listingservice.repository;

import com.motohive.listingservice.entity.Listing;
import com.motohive.listingservice.enums.ListingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {
	
	// check one active listing per vehicle
	boolean existsByVehicleIdAndStatus(Long vehicleId, ListingStatus status);
	
	Optional<Listing> findByVehicleIdAndStatus(Long vehicleId, ListingStatus status);
	
	List<Listing> findBySellerIdOrderByListedAtDesc(Long sellerId);
	
	List<Listing> findBySellerIdAndStatusOrderByListedAtDesc(Long sellerId, ListingStatus status);
	
	List<Listing> findByStatusOrderByListedAtDesc(ListingStatus status);
	
	// dashboard — sold listings in date range
	@Query("""
			SELECT l FROM Listing l
			WHERE l.status = 'SOLD'
			AND l.soldAt BETWEEN :from AND :to
			""")
	List<Listing> findSoldListingsBetween(@Param("from") java.time.LocalDateTime from,
			@Param("to") java.time.LocalDateTime to);
	
}