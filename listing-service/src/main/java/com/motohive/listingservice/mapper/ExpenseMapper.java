package com.motohive.listingservice.mapper;

import com.motohive.listingservice.dto.request.CreateExpenseRequest;
import com.motohive.listingservice.dto.response.ExpenseResponse;
import com.motohive.listingservice.entity.VehicleExpense;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {
	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "listing", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	VehicleExpense toEntity(CreateExpenseRequest request);
	
	@Mapping(target = "listingId", source = "listing.id")
	ExpenseResponse toResponse(VehicleExpense expense);
	
}