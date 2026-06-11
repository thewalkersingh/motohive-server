package com.motohive.vehicleservice.dto.request;
import com.motohive.vehicleservice.enums.BikeType;
import com.motohive.vehicleservice.enums.TyreType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BikeDetailsRequest {
	
	@NotNull(message = "Engine CC is required")
	@Min(value = 50, message = "Engine CC must be at least 50")
	@Max(value = 3000, message = "Engine CC must be at most 3000")
	private Integer engineCc;
	
	@NotNull(message = "Bike type is required")
	private BikeType bikeType;
	private Boolean abs;
	
	@NotNull(message = "Tyre type is required")
	private TyreType tyreType;
	
}