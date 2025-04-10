package com.marcusbike.marcus_bike_api.dto.request;

import com.marcusbike.marcus_bike_api.validations.Step1;
import com.marcusbike.marcus_bike_api.validations.Step2;
import com.marcusbike.marcus_bike_api.validations.Step3;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserInsertDTO {

    @NotBlank(message = "Username field is mandatory", groups = Step1.class)
    @Size(max = 50, message = "Username field can't have more than 50 characters", groups = Step2.class)
    String username;

    @NotBlank(message = "Email field is mandatory", groups = Step1.class)
    @Size(max = 254, message = "Email field can't have more than 254 characters", groups = Step2.class)
    @Email(message = "Email format is not correct", groups = Step3.class)
    String email;

    @NotBlank(message = "Password field is mandatory", groups = Step1.class)
    @Size(min = 8, max = 100, message = "Password must have between 8 and 100 characters", groups = Step2.class)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d])\\S{8,}$", message = "Password field must include uppercase, lowercase, numbers and a special character", groups = Step3.class)
    String password;

    @NotBlank(message = "Role field is mandatory", groups = Step1.class)
    @Pattern(regexp = "^(USER|ADMIN)$", message = "Role must be USER or ADMIN", groups = Step2.class)
    String role;

}