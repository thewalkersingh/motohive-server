package com.motohive.listingservice.controller;

import com.motohive.listingservice.dto.request.CreateListingRequest;
import com.motohive.listingservice.dto.request.UpdateListingRequest;
import com.motohive.listingservice.dto.response.ListingResponse;
import com.motohive.listingservice.dto.response.ListingSummaryResponse;
import com.motohive.listingservice.service.ListingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/listings")
@RequiredArgsConstructor
@Slf4j
public class ListingController {
	
	private final ListingService listingService;
	
	@PostMapping
	public ResponseEntity<ListingResponse> createListing(
			@Valid @RequestBody CreateListingRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED)
				       .body(listingService.createListing(request));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ListingResponse> getListingById(@PathVariable Long id) {
		return ResponseEntity.ok(listingService.getListingById(id));
	}
	
	@GetMapping
	public ResponseEntity<List<ListingSummaryResponse>> getActiveListings() {
		return ResponseEntity.ok(listingService.getActiveListings());
	}
	
	@GetMapping("/seller/{sellerId}")
	public ResponseEntity<List<ListingSummaryResponse>> getListingsBySeller(
			@PathVariable Long sellerId) {
		return ResponseEntity.ok(listingService.getListingsBySeller(sellerId));
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<ListingResponse> updateListing(
			@PathVariable Long id,
			@Valid @RequestBody UpdateListingRequest request) {
		return ResponseEntity.ok(listingService.updateListing(id, request));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteListing(@PathVariable Long id) {
		listingService.deleteListing(id);
		return ResponseEntity.noContent().build();
	}
	
}