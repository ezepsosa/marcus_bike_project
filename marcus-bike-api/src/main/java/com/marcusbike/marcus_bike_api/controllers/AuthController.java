package com.marcusbike.marcus_bike_api.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcusbike.marcus_bike_api.dto.request.AuthRequest;
import com.marcusbike.marcus_bike_api.dto.response.AuthResponse;
import com.marcusbike.marcus_bike_api.models.User;
import com.marcusbike.marcus_bike_api.repositories.UserRepository;
import com.marcusbike.marcus_bike_api.security.JwtProperties;
import com.marcusbike.marcus_bike_api.services.AuthService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtProperties jwtProperties;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest request, HttpServletResponse response) {
        try {
            AuthResponse authResponse = authService.login(request.getEmail(), request.getPassword());

            // Access Cookie
            ResponseCookie accessCookie = ResponseCookie.from("access_token", authResponse.getToken()).httpOnly(false)
                    .secure(true).sameSite("Lax").path("/").maxAge(jwtProperties.getExpiration()).build();

            // Refresh cookie
            ResponseCookie refreshCookie = ResponseCookie.from("refresh_token", authResponse.getRefreshToken())
                    .httpOnly(false).secure(true).sameSite("Lax").path("/").maxAge(jwtProperties.getRefreshExpiration())
                    .build();

            response.addHeader("Set-Cookie", accessCookie.toString());
            response.addHeader("Set-Cookie", refreshCookie.toString());

            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Succesfully authenticated");

        } catch (Exception e) {
            System.err.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

}
