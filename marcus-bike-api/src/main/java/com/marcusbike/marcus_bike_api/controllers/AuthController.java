package com.marcusbike.marcus_bike_api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.marcusbike.marcus_bike_api.dto.request.AuthRequest;
import com.marcusbike.marcus_bike_api.dto.response.AuthResponse;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
public class AuthController {

    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        AuthResponse token = AuthResponse.builder().token("s").build();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(token);

    }

}
