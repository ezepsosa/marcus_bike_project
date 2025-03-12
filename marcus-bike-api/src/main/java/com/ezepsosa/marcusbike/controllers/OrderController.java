package com.ezepsosa.marcusbike.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezepsosa.marcusbike.dto.OrderDTO;
import com.ezepsosa.marcusbike.routes.RouteRegistrar;
import com.ezepsosa.marcusbike.services.OrderService;
import com.ezepsosa.marcusbike.utils.JsonResponseUtil;
import com.ezepsosa.marcusbike.utils.RequestUtils;

import io.undertow.server.HttpServerExchange;
import io.undertow.server.RoutingHandler;
import io.undertow.util.Methods;

public class OrderController implements RouteRegistrar {

    private final OrderService orderService;
    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public void registerRoutes(RoutingHandler router) {
        router.add(Methods.GET, "/orders", this::getAll);
        router.add(Methods.GET, "/users/{userId}/orders", this::getByUserId);
        router.add(Methods.GET, "/orders/{id}", this::getById);
        router.add(Methods.POST, "/orders", this::insert);

    }

    public void getAll(HttpServerExchange exchange) {
        logger.info("Received request: GET /orders");
        List<OrderDTO> orders = orderService.getAll();
        JsonResponseUtil.sendJsonResponse(exchange, orders);
        logger.info("Responded with {} orders", orders.size());

    }

    public void getByUserId(HttpServerExchange exchange) {
        logger.info("Received request: GET /orders/{userId}/orders");
        Long userId = RequestUtils.getRequestParam(exchange, "userId");
        if (userId == null) {
            logger.warn("Invalid or missing user ID");
            JsonResponseUtil.sendErrorResponse(exchange, 400, "Invalid or missing order ID");
            return;
        }
        List<OrderDTO> orders = orderService.getByUserId(userId);
        JsonResponseUtil.sendJsonResponse(exchange, orders);
        logger.info("Responded with {} orders", userId);

    }

    public void getById(HttpServerExchange exchange) {
        logger.info("Received request: GET /orders/{id}");
        Long orderId = RequestUtils.getRequestParam(exchange, "id");
        if (orderId == null) {
            logger.warn("Invalid or missing order ID");
            JsonResponseUtil.sendErrorResponse(exchange, 400, "Invalid or missing order ID");
            return;
        }
        OrderDTO orders = orderService.getById(orderId);
        JsonResponseUtil.sendJsonResponse(exchange, orders);
        logger.info("Responded with {} orders", orderId);

    }

    public void getOrderLineProductPartsById(HttpServerExchange exchange) {
        logger.info("Received request: GET /orders/{id}");
        Long orderId = RequestUtils.getRequestParam(exchange, "id");
        if (orderId == null) {
            logger.warn("Invalid or missing order ID");
            JsonResponseUtil.sendErrorResponse(exchange, 400, "Invalid or missing order ID");
            return;
        }
        OrderDTO orders = orderService.getById(orderId);
        JsonResponseUtil.sendJsonResponse(exchange, orders);
        logger.info("Responded with {} orders", orderId);

    }

    public void insert(HttpServerExchange exchange) {
        logger.info("Received request: POST /order");
        Long orderId = RequestUtils.getRequestParam(exchange, "id");
        if (orderId == null) {
            logger.warn("Invalid or missing order ID");
            JsonResponseUtil.sendErrorResponse(exchange, 400, "Invalid or missing order ID");
            return;
        }
        OrderDTO orders = orderService.getById(orderId);
        JsonResponseUtil.sendJsonResponse(exchange, orders);
        logger.info("Responded with {} orders", orderId);

    }

}
