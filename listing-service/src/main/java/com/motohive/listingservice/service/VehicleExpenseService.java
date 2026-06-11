package com.motohive.listingservice.service;

import com.motohive.listingservice.dto.request.CreateExpenseRequest;
import com.motohive.listingservice.dto.response.ExpenseResponse;

import java.math.BigDecimal;
import java.util.List;

public interface VehicleExpenseService {
	
	ExpenseResponse addExpense(CreateExpenseRequest request);
	
	List<ExpenseResponse> getExpensesByVehicle(Long vehicleId);
	
	BigDecimal getTotalExpenseByVehicle(Long vehicleId);
	
}