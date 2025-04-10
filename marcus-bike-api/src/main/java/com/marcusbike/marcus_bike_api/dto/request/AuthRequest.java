package com.marcusbike.marcus_bike_api.dto.request;

import com.marcusbike.marcus_bike_api.validations.Step1;
import com.marcusbike.marcus_bike_api.validations.Step2;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AuthRequest {
    @NotBlank(message = "Email field is mandatory", groups = Step1.class)
    @Email(message = "Email format is not correct", groups = Step2.class)
    String email;

    @NotBlank(message = "Password field is mandatory", groups = Step1.class)
    String password;
}
