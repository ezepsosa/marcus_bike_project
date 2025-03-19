package com.ezepsosa.marcusbike.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezepsosa.marcusbike.dto.AuthTokenInsert;
import com.ezepsosa.marcusbike.dto.AuthTokenResponse;
import com.ezepsosa.marcusbike.dto.UserDTO;
import com.ezepsosa.marcusbike.routes.RouteRegistrar;
import com.ezepsosa.marcusbike.services.AuthService;
import com.ezepsosa.marcusbike.services.UserService;
import com.ezepsosa.marcusbike.utils.JsonResponseUtil;

import io.undertow.server.HttpServerExchange;
import io.undertow.server.RoutingHandler;
import io.undertow.util.Methods;
import io.undertow.util.StatusCodes;

public class AuthController implements RouteRegistrar {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authservice;
    private final UserService userService;

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
                UserDTO user = userService.getUserByUsernamePassword(userToAuthenticate.username(),
                        userToAuthenticate.password());
                if (user != null) {
                    String token = authservice.login(user.username(), user.role());
                    logger.warn("Invalid login attempt for user: {}", userToAuthenticate.username());
                    AuthTokenResponse response = new AuthTokenResponse(user.id(), user.username(), user.role(), token);
                    JsonResponseUtil.sendJsonResponse(exchange, response);

                } else {
                    JsonResponseUtil.sendErrorResponse(exchange, StatusCodes.UNAUTHORIZED, "Invalid credentials");
                }
            } catch (Exception e) {
                logger.error("Error processing request", e);
                JsonResponseUtil.sendErrorResponse(exchange, StatusCodes.INTERNAL_SERVER_ERROR, "Invalid request body");
            }

        });
    }
}