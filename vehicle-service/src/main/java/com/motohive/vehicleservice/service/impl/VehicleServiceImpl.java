package com.motohive.vehicleservice.service.impl;
import com.motohive.vehicleservice.dto.request.CreateVehicleRequest;
import com.motohive.vehicleservice.dto.request.UpdateVehicleRequest;
import com.motohive.vehicleservice.dto.response.VehicleResponse;
import com.motohive.vehicleservice.entity.*;
import com.motohive.vehicleservice.enums.*;
import com.motohive.vehicleservice.exception.*;
import com.motohive.vehicleservice.mapper.VehicleMapper;
import com.motohive.vehicleservice.repository.*;
import com.motohive.vehicleservice.service.VehicleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class VehicleServiceImpl implements VehicleService {
	
	private final VehicleRepository vehicleRepository;
	private final CarDetailsRepository carDetailsRepository;
	private final BikeDetailsRepository bikeDetailsRepository;
	private final EvDetailsRepository evDetailsRepository;
	private final VehicleMapper vehicleMapper;
	
	// ── Create ───────────────────────────────────────
	
	@Override
	@Transactional
	public VehicleResponse createVehicle(CreateVehicleRequest request) {
		log.info("Creating vehicle with registration: {}", request.getRegistrationNumber());
		
		// 1. duplicate registration check
		if (vehicleRepository.existsByRegistrationNumber(request.getRegistrationNumber())) {
			throw new DuplicateRegistrationException(request.getRegistrationNumber());
		}
		
		// 2. validate type-specific details are present
		validateTypeDetails(request);
		
		// 3. map core fields and save vehicle first (need the generated ID)
		Vehicle vehicle = vehicleMapper.toEntity(request);
		vehicle = vehicleRepository.save(vehicle);
		
		// 4. save type-specific details
		saveTypeDetails(vehicle, request);
		
		// 5. reload with all associations for response mapping
		vehicle = vehicleRepository.findById(vehicle.getId()).orElseThrow();
		
		log.info("Vehicle created with id: {}", vehicle.getId());
		return vehicleMapper.toResponse(vehicle);
	}
	
	// ── Read ─────────────────────────────────────────
	
	@Override
	@Transactional(readOnly = true)
	public VehicleResponse getVehicleById(Long id) {
		Vehicle vehicle = vehicleRepository.findById(id)
				                  .orElseThrow(() -> new VehicleNotFoundException(id));
		return vehicleMapper.toResponse(vehicle);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<VehicleResponse> getVehiclesBySeller(Long sellerId) {
		return vehicleRepository.findBySellerId(sellerId)
				       .stream()
				       .map(vehicleMapper::toResponse)
				       .toList();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<VehicleResponse> searchVehicles(
			VehicleType vehicleType,
			String brand,
			String city,
			FuelType fuelType,
			Transmission transmission) {
		
		return vehicleRepository
				       .searchVehicles(vehicleType, brand, city, fuelType, transmission, VehicleStatus.ACTIVE)
				       .stream()
				       .map(vehicleMapper::toResponse)
				       .toList();
	}
	
	// ── Update ───────────────────────────────────────
	
	@Override
	@Transactional
	public VehicleResponse updateVehicle(Long id, UpdateVehicleRequest request) {
		log.info("Updating vehicle with id: {}", id);
		
		Vehicle vehicle = vehicleRepository.findById(id)
				                  .orElseThrow(() -> new VehicleNotFoundException(id));
		
		// only apply non-null fields (partial update)
		if (request.getColor() != null) vehicle.setColor(request.getColor());
		if (request.getCondition() != null) vehicle.setCondition(request.getCondition());
		if (request.getInsuranceExpiry() != null) vehicle.setInsuranceExpiry(request.getInsuranceExpiry());
		if (request.getMileageKmpl() != null) vehicle.setMileageKmpl(request.getMileageKmpl());
		if (request.getOdometerKm() != null) vehicle.setOdometerKm(request.getOdometerKm());
		if (request.getCity() != null) vehicle.setCity(request.getCity());
		if (request.getStatus() != null) vehicle.setStatus(request.getStatus());
		if (request.getNumberOfOwners() != null) vehicle.setNumberOfOwners(request.getNumberOfOwners());
		
		vehicle = vehicleRepository.save(vehicle);
		return vehicleMapper.toResponse(vehicle);
	}
	
	// ── Delete ───────────────────────────────────────
	
	@Override
	@Transactional
	public void deleteVehicle(Long id) {
		log.info("Deleting vehicle with id: {}", id);
		
		if (!vehicleRepository.existsById(id)) {
			throw new VehicleNotFoundException(id);
		}
		
		// detail rows are cascade deleted via FK in DB
		vehicleRepository.deleteById(id);
		log.info("Vehicle deleted with id: {}", id);
	}
	
	// ── Private helpers ───────────────────────────────
	
	private void validateTypeDetails(CreateVehicleRequest request) {
		switch (request.getVehicleType()) {
			case CAR -> {
				if (request.getCarDetails() == null)
					throw new InvalidVehicleTypeException("Car details are required for vehicle type CAR");
			}
			case BIKE -> {
				if (request.getBikeDetails() == null)
					throw new InvalidVehicleTypeException("Bike details are required for vehicle type BIKE");
			}
			case EV -> {
				if (request.getEvDetails() == null)
					throw new InvalidVehicleTypeException("EV details are required for vehicle type EV");
			}
		}
	}
	
	private void saveTypeDetails(Vehicle vehicle, CreateVehicleRequest request) {
		switch (vehicle.getVehicleType()) {
			case CAR -> {
				CarDetails car = CarDetails.builder()
						                 .vehicle(vehicle)
						                 .engineCc(request.getCarDetails().getEngineCc())
						                 .seatingCapacity(request.getCarDetails().getSeatingCapacity())
						                 .bootSpaceLitres(request.getCarDetails().getBootSpaceLitres())
						                 .airbags(request.getCarDetails().getAirbags())
						                 .driveType(request.getCarDetails().getDriveType())
						                 .sunroof(request.getCarDetails().getSunroof())
						                 .abs(request.getCarDetails().getAbs())
						                 .build();
				carDetailsRepository.save(car);
			}
			case BIKE -> {
				BikeDetails bike = BikeDetails.builder()
						                   .vehicle(vehicle)
						                   .engineCc(request.getBikeDetails().getEngineCc())
						                   .bikeType(request.getBikeDetails().getBikeType())
						                   .abs(request.getBikeDetails().getAbs())
						                   .tyreType(request.getBikeDetails().getTyreType())
						                   .build();
				bikeDetailsRepository.save(bike);
			}
			case EV -> {
				EvDetails ev = EvDetails.builder()
						               .vehicle(vehicle)
						               .batteryCapacityKwh(request.getEvDetails().getBatteryCapacityKwh())
						               .rangeKm(request.getEvDetails().getRangeKm())
						               .chargeTimeHours(request.getEvDetails().getChargeTimeHours())
						               .connectorType(request.getEvDetails().getConnectorType())
						               .topSpeedKmph(request.getEvDetails().getTopSpeedKmph())
						               .driveType(request.getEvDetails().getDriveType())
						               .build();
				evDetailsRepository.save(ev);
			}
		}
	}
	
}