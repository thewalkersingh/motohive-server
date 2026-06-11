package com.motohive.vehicleservice.controller;
import com.motohive.vehicleservice.dto.request.CreateVehicleRequest;
import com.motohive.vehicleservice.dto.request.UpdateVehicleRequest;
import com.motohive.vehicleservice.dto.response.VehicleResponse;
import com.motohive.vehicleservice.enums.FuelType;
import com.motohive.vehicleservice.enums.Transmission;
import com.motohive.vehicleservice.enums.VehicleType;
import com.motohive.vehicleservice.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicles")
@RequiredArgsConstructor
@Slf4j
public class VehicleController {
	
	private final VehicleService vehicleService;
	
	// ── POST /api/v1/vehicles ─────────────────────────
	@PostMapping
	public ResponseEntity<VehicleResponse> createVehicle(
			@Valid @RequestBody CreateVehicleRequest request) {
		
		log.info("POST /api/v1/vehicles - type: {}", request.getVehicleType());
		VehicleResponse response = vehicleService.createVehicle(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	// ── GET /api/v1/vehicles/{id} ─────────────────────
	@GetMapping("/{id}")
	public ResponseEntity<VehicleResponse> getVehicleById(@PathVariable Long id) {
		log.info("GET /api/v1/vehicles/{}", id);
		return ResponseEntity.ok(vehicleService.getVehicleById(id));
	}
	
	// ── GET /api/v1/vehicles/seller/{sellerId} ────────
	@GetMapping("/seller/{sellerId}")
	public ResponseEntity<List<VehicleResponse>> getVehiclesBySeller(
			@PathVariable Long sellerId) {
		
		log.info("GET /api/v1/vehicles/seller/{}", sellerId);
		return ResponseEntity.ok(vehicleService.getVehiclesBySeller(sellerId));
	}
	
	// ── GET /api/v1/vehicles/search ───────────────────
	// Example: /api/v1/vehicles/search?vehicleType=CAR&brand=Toyota&city=Delhi
	@GetMapping("/search")
	public ResponseEntity<List<VehicleResponse>> searchVehicles(
			@RequestParam(required = false) VehicleType vehicleType,
			@RequestParam(required = false) String brand,
			@RequestParam(required = false) String city,
			@RequestParam(required = false) FuelType fuelType,
			@RequestParam(required = false) Transmission transmission) {
		
		log.info("GET /api/v1/vehicles/search - type:{} brand:{} city:{}",
				vehicleType, brand, city);
		return ResponseEntity.ok(
				vehicleService.searchVehicles(vehicleType, brand, city, fuelType, transmission));
	}
	
	// ── PATCH /api/v1/vehicles/{id} ───────────────────
	@PatchMapping("/{id}")
	public ResponseEntity<VehicleResponse> updateVehicle(
			@PathVariable Long id,
			@Valid @RequestBody UpdateVehicleRequest request) {
		
		log.info("PATCH /api/v1/vehicles/{}", id);
		return ResponseEntity.ok(vehicleService.updateVehicle(id, request));
	}
	
	// ── DELETE /api/v1/vehicles/{id} ──────────────────
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
		log.info("DELETE /api/v1/vehicles/{}", id);
		vehicleService.deleteVehicle(id);
		return ResponseEntity.noContent().build();  // 204
	}
	
}