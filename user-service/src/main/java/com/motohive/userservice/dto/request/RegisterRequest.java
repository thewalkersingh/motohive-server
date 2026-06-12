package com.motohive.userservice.dto.request;

import com.motohive.userservice.enums.Gender;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterRequest {
	
	@NotBlank(message = "First name is required")
	@Size(max = 50)
	private String firstName;
	
	@NotBlank(message = "Last name is required")
	@Size(max = 50)
	private String lastName;
	
	@NotBlank(message = "Email is required")
	@Email(message = "Invalid email format")
	private String email;
	
	@NotBlank(message = "Password is required")
	@Size(min = 8, message = "Password must be at least 8 characters")
	private String password;
	
	@Pattern(regexp = "^[0-9]{10}$", message = "Phone must be 10 digits")
	private String phone;
	private Gender gender;
	
	@Past(message = "Date of birth must be in the past")
	private LocalDate dateOfBirth;
	
}