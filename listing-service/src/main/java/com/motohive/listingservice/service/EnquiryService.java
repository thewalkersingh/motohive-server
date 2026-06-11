package com.motohive.listingservice.service;

import com.motohive.listingservice.dto.request.CreateEnquiryRequest;
import com.motohive.listingservice.dto.response.EnquiryResponse;
import com.motohive.listingservice.enums.EnquiryStatus;

import java.util.List;

public interface EnquiryService {
	
	EnquiryResponse createEnquiry(CreateEnquiryRequest request);
	
	List<EnquiryResponse> getEnquiriesByListing(Long listingId);
	
	List<EnquiryResponse> getEnquiriesByBuyer(Long buyerId);
	
	EnquiryResponse updateEnquiryStatus(Long enquiryId, EnquiryStatus status);
	
}