package com.motohive.listingservice.service.impl;

import com.motohive.listingservice.dto.request.CreateEnquiryRequest;
import com.motohive.listingservice.dto.response.EnquiryResponse;
import com.motohive.listingservice.entity.Listing;
import com.motohive.listingservice.entity.ListingEnquiry;
import com.motohive.listingservice.enums.EnquiryStatus;
import com.motohive.listingservice.exception.EnquiryNotFoundException;
import com.motohive.listingservice.exception.ListingNotFoundException;
import com.motohive.listingservice.mapper.ListingMapper;
import com.motohive.listingservice.repository.ListingEnquiryRepository;
import com.motohive.listingservice.repository.ListingRepository;
import com.motohive.listingservice.service.EnquiryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EnquiryServiceImpl implements EnquiryService {
	
	private final ListingEnquiryRepository enquiryRepository;
	private final ListingRepository listingRepository;
	private final ListingMapper listingMapper;
	
	@Override
	@Transactional
	public EnquiryResponse createEnquiry(CreateEnquiryRequest request) {
		Listing listing = listingRepository.findById(request.getListingId())
				                  .orElseThrow(() -> new ListingNotFoundException(request.getListingId()));
		
		ListingEnquiry enquiry = ListingEnquiry.builder()
				                         .listing(listing)
				                         .buyerId(request.getBuyerId())
				                         .message(request.getMessage())
				                         .contactNumber(request.getContactNumber())
				                         .build();
		
		return listingMapper.toEnquiryResponse(enquiryRepository.save(enquiry));
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<EnquiryResponse> getEnquiriesByListing(Long listingId) {
		return enquiryRepository.findByListingIdOrderByCreatedAtDesc(listingId)
				       .stream().map(listingMapper::toEnquiryResponse).toList();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<EnquiryResponse> getEnquiriesByBuyer(Long buyerId) {
		return enquiryRepository.findByBuyerIdOrderByCreatedAtDesc(buyerId)
				       .stream().map(listingMapper::toEnquiryResponse).toList();
	}
	
	@Override
	@Transactional
	public EnquiryResponse updateEnquiryStatus(Long enquiryId, EnquiryStatus status) {
		ListingEnquiry enquiry = enquiryRepository.findById(enquiryId)
				                         .orElseThrow(() -> new EnquiryNotFoundException(enquiryId));
		enquiry.setStatus(status);
		return listingMapper.toEnquiryResponse(enquiryRepository.save(enquiry));
	}
	
}