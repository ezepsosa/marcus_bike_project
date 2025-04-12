package com.marcusbike.marcus_bike_api.exceptions;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.marcusbike.marcus_bike_api.dto.exceptions.ErrorResponse;
import com.marcusbike.marcus_bike_api.dto.exceptions.ValidationErrorResponse;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// Exception for login phase with invalid credentials
	@ExceptionHandler(InvalidCredentialsException.class)
	public ResponseEntity<ErrorResponse> handleInvalidCredentials(InvalidCredentialsException ex) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("error", ex.getMessage()));
	}

	// Exception in case email is already in use
	@ExceptionHandler(EmailAlreadyUsedException.class)
	public ResponseEntity<ErrorResponse> handleInvalidEmail(EmailAlreadyUsedException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse("error", ex.getMessage()));
	}

	// Exception in case username is already in use
	@ExceptionHandler(UsernameAlreadyUsedException.class)
	public ResponseEntity<ErrorResponse> handleInvalidUsername(UsernameAlreadyUsedException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse("error", ex.getMessage()));
	}

	// Handling field exceptions from jakarta
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationErrorResponse> handleValidationError(MethodArgumentNotValidException ex) {
		Map<String, String> uniqueFieldErrors = new LinkedHashMap<>();

		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			uniqueFieldErrors.putIfAbsent(error.getField(), error.getDefaultMessage());
		}

		List<ErrorResponse> errors = uniqueFieldErrors.entrySet().stream()
				.map(error -> new ErrorResponse(error.getKey(), error.getValue())).collect(Collectors.toList());
		return ResponseEntity.badRequest().body(new ValidationErrorResponse(errors));
	}

	// Handling empty result data access exception
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ErrorResponse> handleDataIntegration(DataIntegrityViolationException ex){
		return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse("error", ex.getMessage()));
	}

	// Exception in case an entity cannot be found
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleEntityNotFoundValidation(EntityNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("error", ex.getMessage()));
	}

	// Exception in any other case
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, String>> handleAnyOtherException(Exception ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", ex.getMessage()));
	}

}
