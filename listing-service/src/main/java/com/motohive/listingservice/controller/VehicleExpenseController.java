package com.motohive.listingservice.controller;

import com.motohive.listingservice.dto.request.CreateExpenseRequest;
import com.motohive.listingservice.dto.response.ExpenseResponse;
import com.motohive.listingservice.service.VehicleExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/expenses")
@RequiredArgsConstructor
public class VehicleExpenseController {
	
	private final VehicleExpenseService expenseService;
	
	@PostMapping
	public ResponseEntity<ExpenseResponse> addExpense(
			@Valid @RequestBody CreateExpenseRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED)
				       .body(expenseService.addExpense(request));
	}
	
	@GetMapping("/vehicle/{vehicleId}")
	public ResponseEntity<List<ExpenseResponse>> getByVehicle(@PathVariable Long vehicleId) {
		return ResponseEntity.ok(expenseService.getExpensesByVehicle(vehicleId));
	}
	
	@GetMapping("/vehicle/{vehicleId}/total")
	public ResponseEntity<BigDecimal> getTotalByVehicle(@PathVariable Long vehicleId) {
		return ResponseEntity.ok(expenseService.getTotalExpenseByVehicle(vehicleId));
	}
	
}