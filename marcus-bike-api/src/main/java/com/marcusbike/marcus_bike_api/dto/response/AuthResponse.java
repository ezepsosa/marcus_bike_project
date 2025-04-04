package com.marcusbike.marcus_bike_api.dto.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AuthResponse {
    String token;
    String RefreshToken;

}
