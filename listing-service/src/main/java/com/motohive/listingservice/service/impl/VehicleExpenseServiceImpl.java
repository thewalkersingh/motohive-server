package com.motohive.listingservice.service.impl;

import com.motohive.listingservice.dto.request.CreateExpenseRequest;
import com.motohive.listingservice.dto.response.ExpenseResponse;
import com.motohive.listingservice.entity.Listing;
import com.motohive.listingservice.entity.VehicleExpense;
import com.motohive.listingservice.mapper.ExpenseMapper;
import com.motohive.listingservice.repository.ListingRepository;
import com.motohive.listingservice.repository.VehicleExpenseRepository;
import com.motohive.listingservice.service.VehicleExpenseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class VehicleExpenseServiceImpl implements VehicleExpenseService {
	
	private final VehicleExpenseRepository expenseRepository;
	private final ListingRepository listingRepository;
	private final ExpenseMapper expenseMapper;
	
	@Override
	@Transactional
	public ExpenseResponse addExpense(CreateExpenseRequest request) {
		VehicleExpense expense = expenseMapper.toEntity(request);
		
		// link to listing if listingId provided
		if (request.getListingId() != null) {
			Listing listing = listingRepository.findById(request.getListingId())
					                  .orElse(null);
			expense.setListing(listing);
		}
		
		return expenseMapper.toResponse(expenseRepository.save(expense));
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<ExpenseResponse> getExpensesByVehicle(Long vehicleId) {
		return expenseRepository.findByVehicleIdOrderByIncurredAtDesc(vehicleId)
				       .stream().map(expenseMapper::toResponse).toList();
	}
	
	@Override
	@Transactional(readOnly = true)
	public BigDecimal getTotalExpenseByVehicle(Long vehicleId) {
		return expenseRepository.sumAmountByVehicleId(vehicleId);
	}
	
}