package com.ezepsosa.marcusbike.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezepsosa.marcusbike.dto.OrderDTO;
import com.ezepsosa.marcusbike.dto.OrderInsertDTO;
import com.ezepsosa.marcusbike.routes.RouteRegistrar;
import com.ezepsosa.marcusbike.security.JwtAuthHandler;
import com.ezepsosa.marcusbike.services.OrderService;
import com.ezepsosa.marcusbike.utils.JsonResponseUtil;
import com.ezepsosa.marcusbike.utils.RequestUtils;

import io.undertow.server.HttpServerExchange;
import io.undertow.server.RoutingHandler;
import io.undertow.util.Methods;
import io.undertow.util.StatusCodes;

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
        router.add(Methods.DELETE, "orders/{id}", new JwtAuthHandler(this::delete, List.of("USER", "ADMIN")));
        router.add(Methods.POST, "/orders", new JwtAuthHandler(this::insert, List.of("USER", "ADMIN")));
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
            JsonResponseUtil.sendErrorResponse(exchange, StatusCodes.BAD_REQUEST, "Invalid or missing order ID");
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
            JsonResponseUtil.sendErrorResponse(exchange, StatusCodes.BAD_REQUEST, "Invalid or missing order ID");
            return;
        }
        OrderDTO order = orderService.getById(orderId);
        if (order == null) {
            logger.warn("No orders found with ID {}", orderId);
            JsonResponseUtil.sendErrorResponse(exchange, StatusCodes.BAD_REQUEST, "No orders found");
            return;
        }
        JsonResponseUtil.sendJsonResponse(exchange, order);
        logger.info("Responded with {} orders", orderId);

    }

    public void getOrderLineProductPartsById(HttpServerExchange exchange) {
        logger.info("Received request: GET /orders/{id}");
        Long orderId = RequestUtils.getRequestParam(exchange, "id");
        if (orderId == null) {
            logger.warn("Invalid or missing order ID");
            JsonResponseUtil.sendErrorResponse(exchange, StatusCodes.BAD_REQUEST, "Invalid or missing order ID");
            return;
        }
        OrderDTO orders = orderService.getById(orderId);
        JsonResponseUtil.sendJsonResponse(exchange, orders);
        logger.info("Responded with {} orders", orderId);

    }

    public void delete(HttpServerExchange exchange) {
        logger.info("Received request: DELETE /order/{id}");
        Long orderId = RequestUtils.getRequestParam(exchange, "id");
        if (orderId == null) {
            logger.warn("Invalid or missing order ID");
            JsonResponseUtil.sendErrorResponse(exchange, StatusCodes.BAD_REQUEST, "Invalid or missing order ID");
            return;
        }
        Boolean deleted = orderService.delete(orderId);
        if (deleted == false) {
            logger.warn("Order with ID {} not deleted or not found", orderId);
            JsonResponseUtil.sendErrorResponse(exchange, 404, "User not deleted or not found");
            return;
        }
        logger.info("Order with ID {} found", orderId);
        JsonResponseUtil.sendJsonResponse(exchange, "Succesfully deleted", 201);

    }

    public void insert(HttpServerExchange exchange) {
        logger.info("Received request: POST /order");
        exchange.getRequestReceiver().receiveFullBytes((ex, message) -> {
            try {
                OrderInsertDTO orderToInsert = JsonResponseUtil.parseJson(message,
                        OrderInsertDTO.class);
                Long orderId = orderService.insert(orderToInsert);
                if (orderId == null) {
                    logger.error("Error inserting order");
                    JsonResponseUtil.sendErrorResponse(exchange, StatusCodes.INTERNAL_SERVER_ERROR,
                            "Failed to insert order");
                    return;
                }
                logger.info("Order created with ID {}", orderId);
                JsonResponseUtil.sendJsonResponse(exchange, orderId, 201);
            } catch (Exception e) {
                logger.error("Error processing request", e);
                JsonResponseUtil.sendErrorResponse(exchange, StatusCodes.INTERNAL_SERVER_ERROR, "Invalid request body");
            }
        });

    }

}
