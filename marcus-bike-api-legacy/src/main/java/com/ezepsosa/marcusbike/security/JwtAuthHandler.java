package com.ezepsosa.marcusbike.security;

import java.util.List;

import com.ezepsosa.marcusbike.utils.JsonResponseUtil;
import com.ezepsosa.marcusbike.utils.JwtUtil;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.StatusCodes;

// Middleware for handling JWT authentication and authorization.  
// Validates the token, extracts the user role, and ensures the user has the required role.  
// No expiration check is implemented as this is a non-production environment for a technical test.
public class JwtAuthHandler implements HttpHandler {

    private final HttpHandler exchangeAuth;
    private final List<String> requestRole;

    public JwtAuthHandler(HttpHandler exchangeAuth, List<String> requestRole) {
        this.requestRole = requestRole;
        this.exchangeAuth = exchangeAuth;
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        String header = exchange.getRequestHeaders().getFirst("Authorization");

        if (header == null || !header.startsWith("Bearer")) {
            exchange.setStatusCode(StatusCodes.UNAUTHORIZED);
            JsonResponseUtil.sendErrorResponse(exchange, StatusCodes.UNAUTHORIZED,
                    "Missing or invalid Authorization header");
            return;
        }
        String token = header.substring(7);
        String role = JwtUtil.getRoleFromToken(token);
        if (role == null) {
            JsonResponseUtil.sendErrorResponse(exchange, StatusCodes.UNAUTHORIZED,
                    "Missing or invalid role");
            return;
        }
        if (!requestRole.contains(role)) {
            JsonResponseUtil.sendErrorResponse(exchange, StatusCodes.UNAUTHORIZED,
                    "Not valid role");
            return;
        }
        exchangeAuth.handleRequest(exchange);
    }

}
