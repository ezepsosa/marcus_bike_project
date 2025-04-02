package com.ezepsosa.marcusbike.dto;

public record AuthTokenResponse(Long userId, String username, String role, String token) {

}
