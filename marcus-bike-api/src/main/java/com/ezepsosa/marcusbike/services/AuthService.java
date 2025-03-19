package com.ezepsosa.marcusbike.services;

import com.ezepsosa.marcusbike.utils.JwtUtil;

public class AuthService {

    public AuthService() {

    }

    public String login(String username, String password) {
        return JwtUtil.generateToken(username);
    }

    public static boolean isAuthenticated(String token) {
        return JwtUtil.validateToken(token);
    }

}
