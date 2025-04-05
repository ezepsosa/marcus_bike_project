package com.marcusbike.marcus_bike_api.exceptions;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.marcusbike.marcus_bike_api.dto.exceptions.FieldValidationError;
import com.marcusbike.marcus_bike_api.dto.exceptions.ValidationErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Exception for login phase with invalid credentials
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<String> handleInvalidCredentials(InvalidCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    // Exception in case email is already in use
    @ExceptionHandler(EmailAlreadyUsedException.class)
    public ResponseEntity<String> handleEmail(EmailAlreadyUsedException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    // Exception in case username is already in use
    @ExceptionHandler(UsernameAlreadyUsedException.class)
    public ResponseEntity<String> handleUsername(UsernameAlreadyUsedException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    // Handling field exceptions from jakarta
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationError(MethodArgumentNotValidException ex) {
        Map<String, String> uniqueFieldErrors = new LinkedHashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            uniqueFieldErrors.putIfAbsent(error.getField(), error.getDefaultMessage());
        }

        List<FieldValidationError> errors = uniqueFieldErrors.entrySet().stream()
                .map(error -> new FieldValidationError(error.getKey(), error.getValue()))
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(new ValidationErrorResponse(errors));
    }

    // Exception in any other case
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAnyOtherException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
}
