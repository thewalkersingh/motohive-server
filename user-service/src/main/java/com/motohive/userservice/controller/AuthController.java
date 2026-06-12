package com.motohive.userservice.controller;

import com.motohive.userservice.dto.request.LoginRequest;
import com.motohive.userservice.dto.request.RegisterRequest;
import com.motohive.userservice.dto.response.AuthResponse;
import com.motohive.userservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Register, login, refresh and logout")
public class AuthController {
	
	private final AuthService authService;
	
	@PostMapping("/register")
	@Operation(summary = "Register a new user", description = "Creates account and returns JWT tokens")
	public ResponseEntity<AuthResponse> register(
			@Valid @RequestBody RegisterRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED)
				       .body(authService.register(request));
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(
			@Valid @RequestBody LoginRequest request) {
		return ResponseEntity.ok(authService.login(request));
	}
	
	@PostMapping("/refresh")
	public ResponseEntity<AuthResponse> refresh(
			@RequestParam String refreshToken) {
		return ResponseEntity.ok(authService.refresh(refreshToken));
	}
	
	@PostMapping("/logout")
	public ResponseEntity<Void> logout(@RequestParam String refreshToken) {
		authService.logout(refreshToken);
		return ResponseEntity.noContent().build();
	}
	
}