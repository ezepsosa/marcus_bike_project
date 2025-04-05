package com.marcusbike.marcus_bike_api.dto.response;

import com.marcusbike.marcus_bike_api.models.Role;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserResponseDTO {
    Long id;
    String username;
    String password;
    Role role;
}
