package com.motohive.userservice.service.impl;

import com.motohive.userservice.dto.request.LoginRequest;
import com.motohive.userservice.dto.request.RegisterRequest;
import com.motohive.userservice.dto.response.AuthResponse;
import com.motohive.userservice.entity.User;
import com.motohive.userservice.enums.Role;
import com.motohive.userservice.enums.UserStatus;
import com.motohive.userservice.exception.EmailAlreadyExistsException;
import com.motohive.userservice.exception.InvalidCredentialsException;
import com.motohive.userservice.exception.PhoneAlreadyExistsException;
import com.motohive.userservice.repository.UserRepository;
import com.motohive.userservice.security.JwtService;
import com.motohive.userservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
	
	private final UserRepository userRepository;
	private final JwtService jwtService;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	@Transactional
	public AuthResponse register(RegisterRequest request) {
		log.info("Registering user: {}", request.getEmail());
		
		if (userRepository.existsByEmail(request.getEmail())) {
			log.info("User already exists by email: {}", request.getEmail());
			throw new EmailAlreadyExistsException(request.getEmail());
		}
		
		if (request.getPhone() != null && userRepository.existsByPhone(request.getPhone()))
			throw new PhoneAlreadyExistsException(request.getPhone());
		
		// default role is BUYER
		Set<Role> roles = new HashSet<>();
		roles.add(Role.BUYER);
		
		User user = User.builder()
				            .firstName(request.getFirstName())
				            .lastName(request.getLastName())
				            .email(request.getEmail())
				            .password(passwordEncoder.encode(request.getPassword()))
				            .phone(request.getPhone())
				            .gender(request.getGender())
				            .dateOfBirth(request.getDateOfBirth())
				            .status(UserStatus.ACTIVE)
				            .roles(roles)
				            .build();
		
		user = saveWithRefreshToken(user);
		
		log.info("User registered with id: {}", user.getId());
		return buildAuthResponse(user);
	}
	
	@Override
	@Transactional
	public AuthResponse login(LoginRequest request) {
		User user = userRepository.findByEmail(request.getEmail())
				            .orElseThrow(InvalidCredentialsException::new);
		
		if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
			throw new InvalidCredentialsException();
		
		user = saveWithRefreshToken(user);
		return buildAuthResponse(user);
	}
	
	@Override
	@Transactional
	public AuthResponse refresh(String refreshToken) {
		User user = userRepository.findByRefreshToken(refreshToken)
				            .orElseThrow(InvalidCredentialsException::new);
		
		if (user.getRefreshTokenExpiry().isBefore(LocalDateTime.now()))
			throw new InvalidCredentialsException();
		
		user = saveWithRefreshToken(user);
		return buildAuthResponse(user);
	}
	
	@Override
	@Transactional
	public void logout(String refreshToken) {
		userRepository.findByRefreshToken(refreshToken).ifPresent(user -> {
			user.setRefreshToken(null);
			user.setRefreshTokenExpiry(null);
			userRepository.save(user);
		});
	}
	
	// ── Private helpers ───────────────────────────────
	private User saveWithRefreshToken(User user) {
		String refreshToken = jwtService.generateRefreshToken();
		user.setRefreshToken(refreshToken);
		user.setRefreshTokenExpiry(LocalDateTime.now()
				                           .plusSeconds(jwtService.getRefreshTokenExpiryMs() / 1000));
		return userRepository.save(user);
	}
	
	private AuthResponse buildAuthResponse(User user) {
		String accessToken = jwtService.generateAccessToken(
				user.getId(), user.getEmail(), user.getRoles());
		
		return AuthResponse.builder()
				       .accessToken(accessToken)
				       .refreshToken(user.getRefreshToken())
				       .tokenType("Bearer")
				       .userId(user.getId())
				       .email(user.getEmail())
				       .roles(user.getRoles().stream()
						              .map(Role::name)
						              .collect(Collectors.toSet()))
				       .build();
	}
	
}