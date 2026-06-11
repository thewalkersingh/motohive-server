package com.motohive.listingservice.service.impl;

import com.motohive.listingservice.client.VehicleClient;
import com.motohive.listingservice.dto.request.CreateListingRequest;
import com.motohive.listingservice.dto.request.UpdateListingRequest;
import com.motohive.listingservice.dto.response.ListingResponse;
import com.motohive.listingservice.dto.response.ListingSummaryResponse;
import com.motohive.listingservice.dto.response.VehicleResponse;
import com.motohive.listingservice.entity.Listing;
import com.motohive.listingservice.enums.ListingStatus;
import com.motohive.listingservice.exception.DuplicateActiveListingException;
import com.motohive.listingservice.exception.ListingNotFoundException;
import com.motohive.listingservice.mapper.ListingMapper;
import com.motohive.listingservice.repository.ListingRepository;
import com.motohive.listingservice.service.ListingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ListingServiceImpl implements ListingService {
	
	private final ListingRepository listingRepository;
	private final ListingMapper listingMapper;
	private final VehicleClient vehicleClient;
	
	// ── Create ───────────────────────────────────────
	@Override
	@Transactional
	public ListingResponse createListing(CreateListingRequest request) {
		log.info("Creating listing for vehicleId: {}", request.getVehicleId());
		
		// one active listing per vehicle
		if (listingRepository.existsByVehicleIdAndStatus(
				request.getVehicleId(), ListingStatus.ACTIVE)) {
			throw new DuplicateActiveListingException(request.getVehicleId());
		}
		
		Listing listing = listingMapper.toEntity(request);
		listing = listingRepository.save(listing);
		
		// fetch vehicle details via Feign
		VehicleResponse vehicle = vehicleClient.getVehicleById(listing.getVehicleId());
		
		ListingResponse response = listingMapper.toResponse(listing);
		response.setVehicle(vehicle);
		
		log.info("Listing created with id: {}", listing.getId());
		return response;
	}
	
	// ── Read ─────────────────────────────────────────
	@Override
	@Transactional(readOnly = true)
	public ListingResponse getListingById(Long id) {
		Listing listing = listingRepository.findById(id)
				                  .orElseThrow(() -> new ListingNotFoundException(id));
		
		VehicleResponse vehicle = vehicleClient.getVehicleById(listing.getVehicleId());
		
		ListingResponse response = listingMapper.toResponse(listing);
		response.setVehicle(vehicle);
		return response;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<ListingSummaryResponse> getListingsBySeller(Long sellerId) {
		return listingRepository
				       .findBySellerIdOrderByListedAtDesc(sellerId)
				       .stream()
				       .map(this::toSummaryWithVehicle)
				       .toList();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<ListingSummaryResponse> getActiveListings() {
		return listingRepository
				       .findByStatusOrderByListedAtDesc(ListingStatus.ACTIVE)
				       .stream()
				       .map(this::toSummaryWithVehicle)
				       .toList();
	}
	
	// ── Update ───────────────────────────────────────
	@Override
	@Transactional
	public ListingResponse updateListing(Long id, UpdateListingRequest request) {
		log.info("Updating listing id: {}", id);
		
		Listing listing = listingRepository.findById(id)
				                  .orElseThrow(() -> new ListingNotFoundException(id));
		
		if (request.getPrice() != null) listing.setPrice(request.getPrice());
		if (request.getExpectedPrice() != null) listing.setExpectedPrice(request.getExpectedPrice());
		if (request.getIsNegotiable() != null) listing.setIsNegotiable(request.getIsNegotiable());
		if (request.getDescription() != null) listing.setDescription(request.getDescription());
		if (request.getMeetingLocation() != null) listing.setMeetingLocation(request.getMeetingLocation());
		if (request.getStatus() != null) {
			listing.setStatus(request.getStatus());
			if (request.getStatus() == ListingStatus.SOLD) {
				listing.setSoldAt(LocalDateTime.now());
			}
		}
		
		listing = listingRepository.save(listing);
		
		VehicleResponse vehicle = vehicleClient.getVehicleById(listing.getVehicleId());
		ListingResponse response = listingMapper.toResponse(listing);
		response.setVehicle(vehicle);
		return response;
	}
	
	// ── Delete ───────────────────────────────────────
	@Override
	@Transactional
	public void deleteListing(Long id) {
		log.info("Deleting listing id: {}", id);
		if (!listingRepository.existsById(id)) {
			throw new ListingNotFoundException(id);
		}
		listingRepository.deleteById(id);
	}
	
	// ── Private helper ────────────────────────────────
	private ListingSummaryResponse toSummaryWithVehicle(Listing listing) {
		ListingSummaryResponse summary = listingMapper.toSummaryResponse(listing);
		try {
			VehicleResponse vehicle = vehicleClient.getVehicleById(listing.getVehicleId());
			summary.setVehicleBrand(vehicle.getBrand());
			summary.setVehicleModel(vehicle.getModel());
			summary.setVehicleYear(vehicle.getYear());
			summary.setVehicleType(vehicle.getVehicleType());
			summary.setCity(vehicle.getCity());
		} catch (Exception e) {
			// vehicle-service down — return listing without vehicle details
			log.warn("Could not fetch vehicle {} for listing {}", listing.getVehicleId(), listing.getId());
		}
		return summary;
	}
	
}