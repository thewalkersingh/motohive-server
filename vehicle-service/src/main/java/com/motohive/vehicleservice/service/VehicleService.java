package com.motohive.vehicleservice.service;

import com.motohive.vehicleservice.dto.request.CreateVehicleRequest;
import com.motohive.vehicleservice.dto.request.UpdateVehicleRequest;
import com.motohive.vehicleservice.dto.response.VehicleResponse;
import com.motohive.vehicleservice.enums.*;

import java.util.List;

public interface VehicleService {
	
	VehicleResponse createVehicle(CreateVehicleRequest request);
	
	VehicleResponse getVehicleById(Long id);
	
	List<VehicleResponse> getVehiclesBySeller(Long sellerId);
	
	List<VehicleResponse> searchVehicles(
			VehicleType vehicleType,
			String brand,
			String city,
			FuelType fuelType,
			Transmission transmission
	);
	
	VehicleResponse updateVehicle(Long id, UpdateVehicleRequest request);
	
	void deleteVehicle(Long id);
}