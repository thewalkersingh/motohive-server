package com.motohive.listingservice.mapper;

import com.motohive.listingservice.dto.request.CreateListingRequest;
import com.motohive.listingservice.dto.response.EnquiryResponse;
import com.motohive.listingservice.dto.response.ListingResponse;
import com.motohive.listingservice.dto.response.ListingSummaryResponse;
import com.motohive.listingservice.entity.Listing;
import com.motohive.listingservice.entity.ListingEnquiry;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ListingMapper {
	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "status", ignore = true)
	@Mapping(target = "listedAt", ignore = true)
	@Mapping(target = "soldAt", ignore = true)
	@Mapping(target = "expiresAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "finalSalePrice", ignore = true)
	@Mapping(target = "enquiries", ignore = true)
	@Mapping(target = "expenses", ignore = true)
	Listing toEntity(CreateListingRequest request);
	
	// toResponse — vehicle field set manually in service after Feign call
	@Mapping(target = "vehicle", ignore = true)
	ListingResponse toResponse(Listing listing);
	
	// summary — populate brief vehicle fields manually in service
	@Mapping(target = "vehicleBrand", ignore = true)
	@Mapping(target = "vehicleModel", ignore = true)
	@Mapping(target = "vehicleYear", ignore = true)
	@Mapping(target = "vehicleType", ignore = true)
	@Mapping(target = "city", ignore = true)
	ListingSummaryResponse toSummaryResponse(Listing listing);
	
	@Mapping(target = "listingId", source = "listing.id")
	EnquiryResponse toEnquiryResponse(ListingEnquiry enquiry);
	
}