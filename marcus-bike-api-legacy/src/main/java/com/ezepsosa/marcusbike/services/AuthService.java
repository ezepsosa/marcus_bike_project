package com.ezepsosa.marcusbike.services;

import com.ezepsosa.marcusbike.utils.JwtUtil;

// Service for handling authentication logic.  
// Generates JWT tokens upon login and validates them for authentication.

public class AuthService {

    public AuthService() {

    }

    public String login(String username, String role) {
        return JwtUtil.generateToken(username, role);
    }

}
