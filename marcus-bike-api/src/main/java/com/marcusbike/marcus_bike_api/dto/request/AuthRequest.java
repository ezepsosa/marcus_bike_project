package com.marcusbike.marcus_bike_api.dto.request;

import lombok.Value;

@Value
public class AuthRequest {
    String email;
    String password;
}
