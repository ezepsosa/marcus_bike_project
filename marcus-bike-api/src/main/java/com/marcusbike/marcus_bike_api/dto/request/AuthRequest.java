package com.marcusbike.marcus_bike_api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class AuthRequest {
    @NotBlank(message = "Email field is mandatory")
    @Email(message = "Email format is not correct")
    String email;

    @NotBlank(message = "Password field is mandatory")
    String password;
}
