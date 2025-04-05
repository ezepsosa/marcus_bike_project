package com.marcusbike.marcus_bike_api.dto.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FieldValidationError {

    private final String field;
    private final String message;
}
