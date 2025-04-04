package com.marcusbike.marcus_bike_api.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.marcusbike.marcus_bike_api.dto.response.AuthResponse;
import com.marcusbike.marcus_bike_api.security.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthResponse login(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            String token = jwtService.generateRefreshToken(username);
            return AuthResponse.builder().token(token).build();
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid credentials");

        }
    }

}
