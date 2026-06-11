package com.motohive.vehicleservice.repository;
import com.motohive.vehicleservice.entity.BikeDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BikeDetailsRepository extends JpaRepository<BikeDetails, Long> {
	
	Optional<BikeDetails> findByVehicleId(Long vehicleId);
	
	boolean existsByVehicleId(Long vehicleId);
	
	void deleteByVehicleId(Long vehicleId);
	
}