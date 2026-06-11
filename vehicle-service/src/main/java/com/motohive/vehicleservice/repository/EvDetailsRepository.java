package com.motohive.vehicleservice.repository;
import com.motohive.vehicleservice.entity.EvDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EvDetailsRepository extends JpaRepository<EvDetails, Long> {
	
	Optional<EvDetails> findByVehicleId(Long vehicleId);
	
	boolean existsByVehicleId(Long vehicleId);
	
	void deleteByVehicleId(Long vehicleId);
	
}