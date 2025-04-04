package com.marcusbike.marcus_bike_api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcusbike.marcus_bike_api.dto.request.AuthRequest;
import com.marcusbike.marcus_bike_api.dto.response.AuthResponse;
import com.marcusbike.marcus_bike_api.services.AuthService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest request) {
        try {
            AuthResponse authResponse = authService.login(request.getUsername(), request.getPassword());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(authResponse.getToken());
        } catch (Exception e) {
            System.err.println(e);
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }

    }

}
