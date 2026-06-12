// service/impl/AppointmentServiceImpl.java
package com.motohive.userservice.service.impl;

import com.motohive.userservice.dto.request.AppointmentRequest;
import com.motohive.userservice.dto.response.AppointmentResponse;
import com.motohive.userservice.entity.User;
import com.motohive.userservice.entity.VehicleAppointment;
import com.motohive.userservice.enums.AppointmentStatus;
import com.motohive.userservice.exception.UserNotFoundException;
import com.motohive.userservice.mapper.UserMapper;
import com.motohive.userservice.repository.UserRepository;
import com.motohive.userservice.repository.VehicleAppointmentRepository;
import com.motohive.userservice.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {
	
	private final VehicleAppointmentRepository appointmentRepository;
	private final UserRepository userRepository;
	private final UserMapper userMapper;
	
	@Override
	@Transactional
	public AppointmentResponse createAppointment(AppointmentRequest request) {
		VehicleAppointment appointment = userMapper.toAppointmentEntity(request);
		
		// link registered user if userId provided
		if (request.getUserId() != null) {
			User user = userRepository.findById(request.getUserId())
					            .orElseThrow(() -> new UserNotFoundException(request.getUserId()));
			appointment.setUser(user);
		}
		
		return userMapper.toAppointmentResponse(appointmentRepository.save(appointment));
	}
	
	@Override
	@Transactional(readOnly = true)
	public AppointmentResponse getAppointmentById(Long id) {
		return appointmentRepository.findById(id)
				       .map(userMapper::toAppointmentResponse)
				       .orElseThrow(() -> new RuntimeException("Appointment not found: " + id));
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<AppointmentResponse> getAppointmentsByVehicle(Long vehicleId) {
		return appointmentRepository.findByVehicleIdOrderByPreferredDateAsc(vehicleId)
				       .stream().map(userMapper::toAppointmentResponse).toList();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<AppointmentResponse> getAppointmentsByUser(Long userId) {
		return appointmentRepository.findByUserIdOrderByPreferredDateDesc(userId)
				       .stream().map(userMapper::toAppointmentResponse).toList();
	}
	
	@Override
	@Transactional
	public AppointmentResponse updateStatus(Long id, AppointmentStatus status) {
		VehicleAppointment appointment = appointmentRepository.findById(id)
				                                 .orElseThrow(() -> new RuntimeException("Appointment not found: " + id));
		appointment.setStatus(status);
		return userMapper.toAppointmentResponse(appointmentRepository.save(appointment));
	}
	
}