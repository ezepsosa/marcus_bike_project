package com.ezepsosa.marcusbike.controllers;

import java.util.List;

import com.ezepsosa.marcusbike.models.User;
import com.ezepsosa.marcusbike.routes.RouteRegistrar;
import com.ezepsosa.marcusbike.services.UserService;
import com.ezepsosa.marcusbike.utils.JsonResponseUtil;

import io.undertow.server.HttpServerExchange;
import io.undertow.server.RoutingHandler;
import io.undertow.util.Methods;

public class UserController implements RouteRegistrar {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void getAll(HttpServerExchange exchange) {
        List<User> users = userService.getAll();
        JsonResponseUtil.sendJsonResponse(exchange, users);
    }

    @Override
    public void registerRoutes(RoutingHandler router) {
        router.add(Methods.GET, "/users", this::getAll);
    }

}
