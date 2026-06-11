package com.motohive.vehicleservice.dto.request;
import com.motohive.vehicleservice.enums.ConnectorType;
import com.motohive.vehicleservice.enums.DriveType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EvDetailsRequest {
	
	@NotNull(message = "Battery capacity is required")
	@DecimalMin(value = "1.0", message = "Battery capacity must be at least 1 kWh")
	private BigDecimal batteryCapacityKwh;
	
	@NotNull(message = "Range is required")
	@Min(value = 50, message = "Range must be at least 50 km")
	private Integer rangeKm;
	
	@DecimalMin(value = "0.5", message = "Charge time must be at least 0.5 hours")
	private BigDecimal chargeTimeHours;
	
	@NotNull(message = "Connector type is required")
	private ConnectorType connectorType;
	
	@Min(value = 50, message = "Top speed must be at least 50 kmph")
	private Integer topSpeedKmph;
	
	@NotNull(message = "Drive type is required")
	private DriveType driveType;
	
}