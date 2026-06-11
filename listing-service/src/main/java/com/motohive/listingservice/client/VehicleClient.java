package com.motohive.listingservice.client;

import com.motohive.listingservice.dto.response.VehicleResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "vehicle-service", url = "${motohive.services.vehicle-service-url}")
public interface VehicleClient {
	
	@GetMapping("/api/v1/vehicles/{id}")
	VehicleResponse getVehicleById(@PathVariable("id") Long id);
	
}