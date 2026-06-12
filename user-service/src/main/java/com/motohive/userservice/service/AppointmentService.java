// service/AppointmentService.java
package com.motohive.userservice.service;

import com.motohive.userservice.dto.request.AppointmentRequest;
import com.motohive.userservice.dto.response.AppointmentResponse;
import com.motohive.userservice.enums.AppointmentStatus;

import java.util.List;

public interface AppointmentService {
	
	AppointmentResponse createAppointment(AppointmentRequest request);
	
	AppointmentResponse getAppointmentById(Long id);
	
	List<AppointmentResponse> getAppointmentsByVehicle(Long vehicleId);
	
	List<AppointmentResponse> getAppointmentsByUser(Long userId);
	
	AppointmentResponse updateStatus(Long id, AppointmentStatus status);
	
}