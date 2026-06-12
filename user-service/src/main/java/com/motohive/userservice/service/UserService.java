// service/UserService.java
package com.motohive.userservice.service;

import com.motohive.userservice.dto.request.AddressRequest;
import com.motohive.userservice.dto.request.UpdateUserRequest;
import com.motohive.userservice.dto.response.AddressResponse;
import com.motohive.userservice.dto.response.UserResponse;
import com.motohive.userservice.enums.Role;

import java.util.List;

public interface UserService {
	
	UserResponse getUserById(Long id);
	
	UserResponse updateUser(Long id, UpdateUserRequest request);
	
	AddressResponse addAddress(Long userId, AddressRequest request);
	
	List<AddressResponse> getAddresses(Long userId);
	
	void addRole(Long userId, Role role);
	
}