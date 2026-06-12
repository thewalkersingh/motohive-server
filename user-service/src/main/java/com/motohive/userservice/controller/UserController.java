package com.motohive.userservice.controller;

import com.motohive.userservice.dto.request.AddressRequest;
import com.motohive.userservice.dto.request.UpdateUserRequest;
import com.motohive.userservice.dto.response.AddressResponse;
import com.motohive.userservice.dto.response.UserResponse;
import com.motohive.userservice.enums.Role;
import com.motohive.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
		return ResponseEntity.ok(userService.getUserById(id));
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<UserResponse> updateUser(
			@PathVariable Long id,
			@Valid @RequestBody UpdateUserRequest request) {
		return ResponseEntity.ok(userService.updateUser(id, request));
	}
	
	@PostMapping("/{id}/addresses")
	public ResponseEntity<AddressResponse> addAddress(
			@PathVariable Long id,
			@Valid @RequestBody AddressRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED)
				       .body(userService.addAddress(id, request));
	}
	
	@GetMapping("/{id}/addresses")
	public ResponseEntity<List<AddressResponse>> getAddresses(@PathVariable Long id) {
		return ResponseEntity.ok(userService.getAddresses(id));
	}
	
	@PostMapping("/{id}/roles")
	public ResponseEntity<Void> addRole(
			@PathVariable Long id,
			@RequestParam Role role) {
		userService.addRole(id, role);
		return ResponseEntity.noContent().build();
	}
	
}