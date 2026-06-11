package com.motohive.vehicleservice.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	// ── 404 ──────────────────────────────────────────
	
	@ExceptionHandler(VehicleNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleVehicleNotFound(VehicleNotFoundException ex) {
		return ResponseEntity
				       .status(HttpStatus.NOT_FOUND)
				       .body(buildError(HttpStatus.NOT_FOUND, ex.getMessage()));
	}
	
	// ── 409 ──────────────────────────────────────────
	
	@ExceptionHandler(DuplicateRegistrationException.class)
	public ResponseEntity<ErrorResponse> handleDuplicateRegistration(DuplicateRegistrationException ex) {
		return ResponseEntity
				       .status(HttpStatus.CONFLICT)
				       .body(buildError(HttpStatus.CONFLICT, ex.getMessage()));
	}
	
	// ── 400 — invalid vehicle type details ───────────
	
	@ExceptionHandler(InvalidVehicleTypeException.class)
	public ResponseEntity<ErrorResponse> handleInvalidVehicleType(InvalidVehicleTypeException ex) {
		return ResponseEntity
				       .status(HttpStatus.BAD_REQUEST)
				       .body(buildError(HttpStatus.BAD_REQUEST, ex.getMessage()));
	}
	
	// ── 400 — @Valid bean validation failures ─────────
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationErrorResponse> handleValidationErrors(
			MethodArgumentNotValidException ex) {
		
		Map<String, String> fieldErrors = new HashMap<>();
		
		ex.getBindingResult()
				.getAllErrors()
				.forEach(error -> {
					String field = ((FieldError) error).getField();
					String message = error.getDefaultMessage();
					fieldErrors.put(field, message);
				});
		
		ValidationErrorResponse response = ValidationErrorResponse.builder()
				                                   .timestamp(LocalDateTime.now())
				                                   .status(HttpStatus.BAD_REQUEST.value())
				                                   .error("Validation Failed")
				                                   .fieldErrors(fieldErrors)
				                                   .build();
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
	
	// ── 500 — catch-all ───────────────────────────────
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
		return ResponseEntity
				       .status(HttpStatus.INTERNAL_SERVER_ERROR)
				       .body(buildError(HttpStatus.INTERNAL_SERVER_ERROR,
						       "An unexpected error occurred"));
	}
	
	// ── Helper ────────────────────────────────────────
	
	private ErrorResponse buildError(HttpStatus status, String message) {
		return ErrorResponse.builder()
				       .timestamp(LocalDateTime.now())
				       .status(status.value())
				       .error(status.getReasonPhrase())
				       .message(message)
				       .build();
	}
	
}