package com.motohive.userservice.repository;

import com.motohive.userservice.entity.VehicleAppointment;
import com.motohive.userservice.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VehicleAppointmentRepository extends JpaRepository<VehicleAppointment, Long> {
	
	List<VehicleAppointment> findByUserIdOrderByPreferredDateDesc(Long userId);
	
	List<VehicleAppointment> findByVehicleIdOrderByPreferredDateAsc(Long vehicleId);
	
	List<VehicleAppointment> findByListingIdOrderByPreferredDateAsc(Long listingId);
	
	List<VehicleAppointment> findByVehicleIdAndStatus(Long vehicleId, AppointmentStatus status);
	
	// check for conflicting appointments on same vehicle, date, time
	boolean existsByVehicleIdAndPreferredDateAndPreferredTimeAndStatusNot(
			Long vehicleId,
			LocalDate preferredDate,
			java.time.LocalTime preferredTime,
			AppointmentStatus status
	);
}