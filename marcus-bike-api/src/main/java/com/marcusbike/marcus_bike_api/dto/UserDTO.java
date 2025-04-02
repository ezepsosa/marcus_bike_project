package com.marcusbike.marcus_bike_api.dto;

import com.marcusbike.marcus_bike_api.models.Role;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserDTO {
    Long id;
    String username;
    String password;
    Role role;
}
