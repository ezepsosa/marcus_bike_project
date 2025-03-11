package com.ezepsosa.marcusbike.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezepsosa.marcusbike.dto.OrderDTO;
import com.ezepsosa.marcusbike.routes.RouteRegistrar;
import com.ezepsosa.marcusbike.services.OrderService;
import com.ezepsosa.marcusbike.utils.JsonResponseUtil;

import io.undertow.server.HttpServerExchange;
import io.undertow.server.RoutingHandler;
import io.undertow.util.Methods;

public class OrderController implements RouteRegistrar {

    private OrderService orderService;
    private Logger logger = LoggerFactory.getLogger(OrderController.class);

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public void registerRoutes(RoutingHandler router) {
        router.add(Methods.GET, "/orders", this::getAll);
    }

    public void getAll(HttpServerExchange exchange) {
        logger.info("Received request: GET /orders");
        List<OrderDTO> orders = orderService.getAll();
        JsonResponseUtil.sendJsonResponse(exchange, orders);
        logger.info("Responded with {} orders", orders.size());

    }
}
