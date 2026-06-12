package com.motohive.userservice.mapper;

import com.motohive.userservice.dto.request.AppointmentRequest;
import com.motohive.userservice.dto.response.AddressResponse;
import com.motohive.userservice.dto.response.AppointmentResponse;
import com.motohive.userservice.dto.response.UserResponse;
import com.motohive.userservice.entity.User;
import com.motohive.userservice.entity.UserAddress;
import com.motohive.userservice.entity.VehicleAppointment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
	
	@Mapping(target = "age", expression = "java(user.getDateOfBirth() != null ? java.time.Period.between(user" +
			                                      ".getDateOfBirth(), java.time.LocalDate.now()).getYears() : null)")
	@Mapping(target = "addresses", source = "addresses")
	UserResponse toResponse(User user);
	
	AddressResponse toAddressResponse(UserAddress address);
	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "status", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "user", ignore = true)
	VehicleAppointment toAppointmentEntity(AppointmentRequest request);
	
	@Mapping(target = "userId", source = "user.id")
	AppointmentResponse toAppointmentResponse(VehicleAppointment appointment);
	
}