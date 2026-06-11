package com.motohive.vehicleservice.dto.request;
import com.motohive.vehicleservice.enums.DriveType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CarDetailsRequest {
	
	@NotNull(message = "Engine CC is required")
	@Min(value = 500, message = "Engine CC must be at least 500")
	@Max(value = 10000, message = "Engine CC must be at most 10000")
	private Integer engineCc;
	
	@NotNull(message = "Seating capacity is required")
	@Min(value = 2, message = "Seating capacity must be at least 2")
	@Max(value = 9, message = "Seating capacity must be at most 9")
	private Byte seatingCapacity;
	
	@Min(value = 0, message = "Boot space cannot be negative")
	private Integer bootSpaceLitres;
	
	@Min(value = 0, message = "Airbags cannot be negative")
	@Max(value = 12, message = "Airbags cannot exceed 12")
	private Byte airbags;
	
	@NotNull(message = "Drive type is required")
	private DriveType driveType;
	private Boolean sunroof;
	private Boolean abs;
	
}