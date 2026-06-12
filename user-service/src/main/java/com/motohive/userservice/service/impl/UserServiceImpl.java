// service/impl/UserServiceImpl.java
package com.motohive.userservice.service.impl;

import com.motohive.userservice.dto.request.AddressRequest;
import com.motohive.userservice.dto.request.UpdateUserRequest;
import com.motohive.userservice.dto.response.AddressResponse;
import com.motohive.userservice.dto.response.UserResponse;
import com.motohive.userservice.entity.User;
import com.motohive.userservice.entity.UserAddress;
import com.motohive.userservice.enums.Role;
import com.motohive.userservice.exception.UserNotFoundException;
import com.motohive.userservice.mapper.UserMapper;
import com.motohive.userservice.repository.UserAddressRepository;
import com.motohive.userservice.repository.UserRepository;
import com.motohive.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	private final UserAddressRepository addressRepository;
	private final UserMapper userMapper;
	
	@Override
	@Transactional(readOnly = true)
	public UserResponse getUserById(Long id) {
		return userMapper.toResponse(findUserById(id));
	}
	
	@Override
	@Transactional
	public UserResponse updateUser(Long id, UpdateUserRequest request) {
		User user = findUserById(id);
		if (request.getFirstName() != null) user.setFirstName(request.getFirstName());
		if (request.getLastName() != null) user.setLastName(request.getLastName());
		if (request.getPhone() != null) user.setPhone(request.getPhone());
		if (request.getProfilePhotoUrl() != null) user.setProfilePhotoUrl(request.getProfilePhotoUrl());
		if (request.getGender() != null) user.setGender(request.getGender());
		if (request.getDateOfBirth() != null) user.setDateOfBirth(request.getDateOfBirth());
		return userMapper.toResponse(userRepository.save(user));
	}
	
	@Override
	@Transactional
	public AddressResponse addAddress(Long userId, AddressRequest request) {
		User user = findUserById(userId);
		
		// if new address is default, clear existing default first
		if (Boolean.TRUE.equals(request.getIsDefault())) {
			addressRepository.clearDefaultByUserId(userId);
		}
		
		UserAddress address = UserAddress.builder()
				                      .user(user)
				                      .addressLine(request.getAddressLine())
				                      .city(request.getCity())
				                      .state(request.getState())
				                      .pincode(request.getPincode())
				                      .isDefault(Boolean.TRUE.equals(request.getIsDefault()))
				                      .build();
		
		return userMapper.toAddressResponse(addressRepository.save(address));
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<AddressResponse> getAddresses(Long userId) {
		return addressRepository.findByUserId(userId)
				       .stream().map(userMapper::toAddressResponse).toList();
	}
	
	@Override
	@Transactional
	public void addRole(Long userId, Role role) {
		User user = findUserById(userId);
		user.getRoles().add(role);
		userRepository.save(user);
	}
	
	private User findUserById(Long id) {
		return userRepository.findById(id)
				       .orElseThrow(() -> new UserNotFoundException(id));
	}
	
}