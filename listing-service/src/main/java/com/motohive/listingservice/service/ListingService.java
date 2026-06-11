package com.motohive.listingservice.service;

import com.motohive.listingservice.dto.request.CreateListingRequest;
import com.motohive.listingservice.dto.request.UpdateListingRequest;
import com.motohive.listingservice.dto.response.ListingResponse;
import com.motohive.listingservice.dto.response.ListingSummaryResponse;

import java.util.List;

public interface ListingService {
	
	ListingResponse createListing(CreateListingRequest request);
	
	ListingResponse getListingById(Long id);
	
	List<ListingSummaryResponse> getListingsBySeller(Long sellerId);
	
	List<ListingSummaryResponse> getActiveListings();
	
	ListingResponse updateListing(Long id, UpdateListingRequest request);
	
	void deleteListing(Long id);
	
}