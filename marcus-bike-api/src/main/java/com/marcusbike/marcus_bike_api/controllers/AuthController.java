package com.marcusbike.marcus_bike_api.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcusbike.marcus_bike_api.dto.request.AuthRequest;
import com.marcusbike.marcus_bike_api.dto.request.UserInsertDTO;
import com.marcusbike.marcus_bike_api.dto.response.AuthResponse;
import com.marcusbike.marcus_bike_api.security.JwtProperties;
import com.marcusbike.marcus_bike_api.services.AuthService;
import com.marcusbike.marcus_bike_api.validations.ValidationSequence;

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
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Validated(ValidationSequence.class) AuthRequest request,
            HttpServletResponse response) {
        logger.info("Authenticating user");
        AuthResponse authResponse = this.authService.login(request.getEmail(), request.getPassword());

        logger.info("Credentials valid. Setting authentication cookies");
        // Access Cookie
        ResponseCookie accessCookie = ResponseCookie.from("access_token", authResponse.getToken()).httpOnly(false)
                .secure(true).sameSite("Lax").path("/").maxAge(this.jwtProperties.getExpiration()).build();

        // Refresh cookie
        ResponseCookie refreshCookie = ResponseCookie.from("refresh_token", authResponse.getRefreshToken())
                .httpOnly(false).secure(true).sameSite("Lax").path("/").maxAge(this.jwtProperties.getRefreshExpiration())
                .build();

        response.addHeader("Set-Cookie", accessCookie.toString());
        response.addHeader("Set-Cookie", refreshCookie.toString());

        this.logger.info("User authenticated successfully");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Succesfully authenticated");

    }

    @PostMapping("/register")
    public ResponseEntity<String> postMethodName(
            @RequestBody @Validated(ValidationSequence.class) UserInsertDTO registerRequest,
            HttpServletResponse response) {
        this.logger.info("Registering user");
        this.authService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Succesfully registered");
    }

}
