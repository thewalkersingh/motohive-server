package com.motohive.userservice.dto.response;

import com.motohive.userservice.enums.Gender;
import com.motohive.userservice.enums.Role;
import com.motohive.userservice.enums.UserStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.Set;

@Data
public class UserResponse {
	
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private String profilePhotoUrl;
	private Gender gender;
	private LocalDate dateOfBirth;
	private Integer age;             // derived from dateOfBirth
	private UserStatus status;
	private Set<Role> roles;
	private List<AddressResponse> addresses;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
}