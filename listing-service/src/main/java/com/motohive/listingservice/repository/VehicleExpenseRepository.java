package com.motohive.listingservice.repository;

import com.motohive.listingservice.entity.VehicleExpense;
import com.motohive.listingservice.enums.ExpenseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface VehicleExpenseRepository extends JpaRepository<VehicleExpense, Long> {
	
	List<VehicleExpense> findByVehicleIdOrderByIncurredAtDesc(Long vehicleId);
	
	List<VehicleExpense> findBySellerIdOrderByIncurredAtDesc(Long sellerId);
	
	List<VehicleExpense> findByVehicleIdAndExpenseType(Long vehicleId, ExpenseType expenseType);
	
	// total expense on a vehicle
	@Query("SELECT COALESCE(SUM(e.amount), 0) FROM VehicleExpense e WHERE e.vehicleId = :vehicleId")
	BigDecimal sumAmountByVehicleId(@Param("vehicleId") Long vehicleId);
	
	// platform fee earnings in date range (for dashboard)
	@Query("""
			SELECT COALESCE(SUM(e.amount), 0) FROM VehicleExpense e
			WHERE e.expenseType = 'PLATFORM_FEE'
			AND e.incurredAt BETWEEN :from AND :to
			""")
	BigDecimal sumPlatformFeesBetween(@Param("from") LocalDate from, @Param("to") LocalDate to);
	
}