package com.marcusbike.marcus_bike_api.dto.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserResponseDTO {
    Long id;
    String email;
    String username;
    String password;
    String role;
}
