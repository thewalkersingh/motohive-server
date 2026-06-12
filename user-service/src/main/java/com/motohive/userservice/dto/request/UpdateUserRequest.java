package com.motohive.userservice.dto.request;

import com.motohive.userservice.enums.Gender;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateUserRequest {
	
	@Size(max = 50)
	private String firstName;
	
	@Size(max = 50)
	private String lastName;
	
	@Pattern(regexp = "^[0-9]{10}$", message = "Phone must be 10 digits")
	private String phone;
	private String profilePhotoUrl;
	private Gender gender;
	
	@Past(message = "Date of birth must be in the past")
	private LocalDate dateOfBirth;
	
}