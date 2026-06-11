package com.motohive.vehicleservice.repository;
import com.motohive.vehicleservice.entity.VehicleImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleImageRepository extends JpaRepository<VehicleImage, Long> {
	
	List<VehicleImage> findByVehicleIdOrderByDisplayOrderAsc(Long vehicleId);
	
	Optional<VehicleImage> findByVehicleIdAndIsPrimaryTrue(Long vehicleId);
	
	int countByVehicleId(Long vehicleId);
	
	// clears primary flag on all images of a vehicle before setting a new one
	@Modifying
	@Query("UPDATE VehicleImage i SET i.isPrimary = false WHERE i.vehicle.id = :vehicleId")
	void clearPrimaryByVehicleId(@Param("vehicleId") Long vehicleId);
	
	void deleteByVehicleId(Long vehicleId);
	
}