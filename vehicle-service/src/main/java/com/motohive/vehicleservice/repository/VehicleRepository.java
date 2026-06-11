package com.motohive.vehicleservice.repository;
import com.motohive.vehicleservice.entity.Vehicle;
import com.motohive.vehicleservice.enums.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
	
	// ── Single lookups ───────────────────────────────
	
	Optional<Vehicle> findByRegistrationNumber(String registrationNumber);
	
	boolean existsByRegistrationNumber(String registrationNumber);
	
	// ── Seller lookups ───────────────────────────────
	
	List<Vehicle> findBySellerId(Long sellerId);
	
	List<Vehicle> findBySellerIdAndStatus(Long sellerId, VehicleStatus status);
	
	// ── Filtering ────────────────────────────────────
	
	List<Vehicle> findByVehicleTypeAndStatus(VehicleType vehicleType, VehicleStatus status);
	
	List<Vehicle> findByCityAndStatus(String city, VehicleStatus status);
	
	List<Vehicle> findByBrandAndModelAndStatus(String brand, String model, VehicleStatus status);
	
	// ── Combined filter (search page) ────────────────
	
	@Query("""
			SELECT v FROM Vehicle v
			WHERE (:vehicleType IS NULL OR v.vehicleType = :vehicleType)
			AND   (:brand       IS NULL OR LOWER(v.brand) = LOWER(:brand))
			AND   (:city        IS NULL OR LOWER(v.city)  = LOWER(:city))
			AND   (:fuelType    IS NULL OR v.fuelType     = :fuelType)
			AND   (:transmission IS NULL OR v.transmission = :transmission)
			AND   v.status = :status
			""")
	List<Vehicle> searchVehicles(
			@Param("vehicleType") VehicleType vehicleType,
			@Param("brand") String brand,
			@Param("city") String city,
			@Param("fuelType") FuelType fuelType,
			@Param("transmission") Transmission transmission,
			@Param("status") VehicleStatus status
	);
	
}