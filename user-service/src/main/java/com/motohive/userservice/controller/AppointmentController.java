package com.motohive.userservice.controller;

import com.motohive.userservice.dto.request.AppointmentRequest;
import com.motohive.userservice.dto.response.AppointmentResponse;
import com.motohive.userservice.enums.AppointmentStatus;
import com.motohive.userservice.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
public class AppointmentController {
	
	private final AppointmentService appointmentService;
	
	@PostMapping
	public ResponseEntity<AppointmentResponse> createAppointment(
			@Valid @RequestBody AppointmentRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED)
				       .body(appointmentService.createAppointment(request));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<AppointmentResponse> getById(@PathVariable Long id) {
		return ResponseEntity.ok(appointmentService.getAppointmentById(id));
	}
	
	@GetMapping("/vehicle/{vehicleId}")
	public ResponseEntity<List<AppointmentResponse>> getByVehicle(
			@PathVariable Long vehicleId) {
		return ResponseEntity.ok(appointmentService.getAppointmentsByVehicle(vehicleId));
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<AppointmentResponse>> getByUser(
			@PathVariable Long userId) {
		return ResponseEntity.ok(appointmentService.getAppointmentsByUser(userId));
	}
	
	@PatchMapping("/{id}/status")
	public ResponseEntity<AppointmentResponse> updateStatus(
			@PathVariable Long id,
			@RequestParam AppointmentStatus status) {
		return ResponseEntity.ok(appointmentService.updateStatus(id, status));
	}
	
}