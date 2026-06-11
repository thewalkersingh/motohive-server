package com.motohive.vehicleservice.repository;
import com.motohive.vehicleservice.entity.CarDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarDetailsRepository extends JpaRepository<CarDetails, Long> {
	
	Optional<CarDetails> findByVehicleId(Long vehicleId);
	
	boolean existsByVehicleId(Long vehicleId);
	
	void deleteByVehicleId(Long vehicleId);
	
}