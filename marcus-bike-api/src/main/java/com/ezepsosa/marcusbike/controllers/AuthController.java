package com.ezepsosa.marcusbike.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezepsosa.marcusbike.dto.AuthTokenInsert;
import com.ezepsosa.marcusbike.routes.RouteRegistrar;
import com.ezepsosa.marcusbike.services.AuthService;
import com.ezepsosa.marcusbike.services.UserService;
import com.ezepsosa.marcusbike.utils.JsonResponseUtil;

import io.undertow.server.HttpServerExchange;
import io.undertow.server.RoutingHandler;
import io.undertow.util.Methods;

public class AuthController implements RouteRegistrar {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private AuthService authservice;
    private UserService userService;

    public AuthController(AuthService authservice, UserService userService) {
        this.authservice = authservice;
        this.userService = userService;
    }

    @Override
    public void registerRoutes(RoutingHandler router) {
        router.add(Methods.POST, "/login", this::authenticate);
    }

    public void authenticate(HttpServerExchange exchange) {
        logger.info("Received login request: POST /login");
        exchange.getRequestReceiver().receiveFullBytes((ex, message) -> {
            try {
                AuthTokenInsert userToAuthenticate = JsonResponseUtil.parseJson(message, AuthTokenInsert.class);
                Boolean userExists = userService.getUserByUsernamePassword(userToAuthenticate.username(),
                        userToAuthenticate.password());
                if (userExists) {
                    String token = authservice.login(userToAuthenticate.username(), userToAuthenticate.password());
                    logger.warn("Invalid login attempt for user: {}", userToAuthenticate.username());
                    JsonResponseUtil.sendJsonResponse(exchange, token);
                } else {
                    JsonResponseUtil.sendErrorResponse(exchange, 401, "Invalid credentials");
                }
            } catch (Exception e) {
                logger.error("Error processing request", e);
                JsonResponseUtil.sendErrorResponse(exchange, 500, "Invalid request body");
            }
        });

    }
}
