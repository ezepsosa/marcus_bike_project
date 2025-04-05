package com.marcusbike.marcus_bike_api.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.marcusbike.marcus_bike_api.dto.request.UserInsertDTO;
import com.marcusbike.marcus_bike_api.dto.response.AuthResponse;
import com.marcusbike.marcus_bike_api.exceptions.EmailAlreadyUsedException;
import com.marcusbike.marcus_bike_api.exceptions.InvalidCredentialsException;
import com.marcusbike.marcus_bike_api.exceptions.UsernameAlreadyUsedException;
import com.marcusbike.marcus_bike_api.models.Role;
import com.marcusbike.marcus_bike_api.models.User;
import com.marcusbike.marcus_bike_api.repositories.UserRepository;
import com.marcusbike.marcus_bike_api.security.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final Logger logger = LoggerFactory.getLogger(AuthService.class);

    public AuthResponse login(String email, String password) {
        try {
            logger.info("Trying to authenticate user with email: ", email);
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (AuthenticationException e) {
            logger.warn("Failed authentication for user:", email);
            throw new InvalidCredentialsException("Invalid credentials");

        }
        String token = jwtService.generateToken(email);
        String refreshToken = jwtService.generateRefreshToken(email);
        logger.info("Successfully authentication for user with email:", email);
        return AuthResponse.builder().token(token).RefreshToken(refreshToken).build();
    }

    public void register(UserInsertDTO registerRequest) {
        logger.info("Checking if email is already in use", registerRequest.getEmail());
        User user = userRepository.findByEmailOrUsername(registerRequest.getEmail(), registerRequest.getUsername())
                .orElse(null);
        if (user != null) {
            if (user.getEmail().equals(registerRequest.getEmail())) {
                logger.warn("Email is already in use");
                throw new EmailAlreadyUsedException("The email is already in use");
            } else {
                logger.warn("Username is already in use");
                throw new UsernameAlreadyUsedException("The username is already in use");
            }
        }
        logger.info("Email validated. Checking if username is already in use.");

        String hashPassword = new BCryptPasswordEncoder().encode(registerRequest.getPassword());
        Role role = Role.valueOf(registerRequest.getRole().toUpperCase());
        user = User.builder().email(registerRequest.getEmail()).username(registerRequest.getUsername())
                .password(hashPassword).role(role).build();

        userRepository.save(user);
    }

}
