package com.motohive.vehicleservice.mapper;
import com.motohive.vehicleservice.dto.request.CreateVehicleRequest;
import com.motohive.vehicleservice.dto.response.VehicleImageResponse;
import com.motohive.vehicleservice.dto.response.VehicleResponse;
import com.motohive.vehicleservice.entity.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring")  // makes it a Spring bean — inject with @Autowired
public interface VehicleMapper {
	
	// ── Entity → Flat Response ────────────────────────
	@Mapping(target = "engineCc", source = "carDetails.engineCc")
	@Mapping(target = "seatingCapacity", source = "carDetails.seatingCapacity")
	@Mapping(target = "bootSpaceLitres", source = "carDetails.bootSpaceLitres")
	@Mapping(target = "airbags", source = "carDetails.airbags")
	@Mapping(target = "driveType", source = "carDetails.driveType")
	@Mapping(target = "sunroof", source = "carDetails.sunroof")
	@Mapping(target = "abs", source = "carDetails.abs")
	@Mapping(target = "bikeType", source = "bikeDetails.bikeType")
	@Mapping(target = "tyreType", source = "bikeDetails.tyreType")
	@Mapping(target = "batteryCapacityKwh", source = "evDetails.batteryCapacityKwh")
	@Mapping(target = "rangeKm", source = "evDetails.rangeKm")
	@Mapping(target = "chargeTimeHours", source = "evDetails.chargeTimeHours")
	@Mapping(target = "connectorType", source = "evDetails.connectorType")
	@Mapping(target = "topSpeedKmph", source = "evDetails.topSpeedKmph")
	@Mapping(target = "images", source = "images")
	VehicleResponse toResponse(Vehicle vehicle);
	
	// ── CreateRequest → Entity ────────────────────────
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "status", ignore = true)  // defaults to ACTIVE in entity
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "carDetails", ignore = true)  // handled separately in service
	@Mapping(target = "bikeDetails", ignore = true)
	@Mapping(target = "evDetails", ignore = true)
	@Mapping(target = "images", ignore = true)
	Vehicle toEntity(CreateVehicleRequest request);
	
	// ── Image Entity → Response ───────────────────────
	VehicleImageResponse toImageResponse(VehicleImage image);
	
}