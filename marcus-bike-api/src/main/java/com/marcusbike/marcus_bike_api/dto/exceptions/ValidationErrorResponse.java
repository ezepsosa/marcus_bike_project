package com.marcusbike.marcus_bike_api.dto.exceptions;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ValidationErrorResponse {
    private final String message = "Validation failed";
    private final List<FieldValidationError> errors;

}
