package com.motohive.userservice.service;

import com.motohive.userservice.dto.request.LoginRequest;
import com.motohive.userservice.dto.request.RegisterRequest;
import com.motohive.userservice.dto.response.AuthResponse;

public interface AuthService {
	
	AuthResponse register(RegisterRequest request);
	
	AuthResponse login(LoginRequest request);
	
	AuthResponse refresh(String refreshToken);
	
	void logout(String refreshToken);
	
}