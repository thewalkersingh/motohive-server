package com.motohive.listingservice.controller;

import com.motohive.listingservice.dto.request.CreateEnquiryRequest;
import com.motohive.listingservice.dto.response.EnquiryResponse;
import com.motohive.listingservice.enums.EnquiryStatus;
import com.motohive.listingservice.service.EnquiryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/enquiries")
@RequiredArgsConstructor
public class EnquiryController {
	
	private final EnquiryService enquiryService;
	
	@PostMapping
	public ResponseEntity<EnquiryResponse> createEnquiry(
			@Valid @RequestBody CreateEnquiryRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED)
				       .body(enquiryService.createEnquiry(request));
	}
	
	@GetMapping("/listing/{listingId}")
	public ResponseEntity<List<EnquiryResponse>> getByListing(@PathVariable Long listingId) {
		return ResponseEntity.ok(enquiryService.getEnquiriesByListing(listingId));
	}
	
	@GetMapping("/buyer/{buyerId}")
	public ResponseEntity<List<EnquiryResponse>> getByBuyer(@PathVariable Long buyerId) {
		return ResponseEntity.ok(enquiryService.getEnquiriesByBuyer(buyerId));
	}
	
	@PatchMapping("/{enquiryId}/status")
	public ResponseEntity<EnquiryResponse> updateStatus(
			@PathVariable Long enquiryId,
			@RequestParam EnquiryStatus status) {
		return ResponseEntity.ok(enquiryService.updateEnquiryStatus(enquiryId, status));
	}
	
}