package com.ezepsosa.marcusbike.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezepsosa.marcusbike.dto.OrderLineDTO;
import com.ezepsosa.marcusbike.dto.OrderLineProductPartDTO;
import com.ezepsosa.marcusbike.routes.RouteRegistrar;
import com.ezepsosa.marcusbike.services.OrderLineProductPartService;
import com.ezepsosa.marcusbike.services.OrderLineService;
import com.ezepsosa.marcusbike.utils.JsonResponseUtil;
import com.ezepsosa.marcusbike.utils.RequestUtils;

import io.undertow.server.HttpServerExchange;
import io.undertow.server.RoutingHandler;
import io.undertow.util.Methods;
import io.undertow.util.StatusCodes;

public class OrderLineController implements RouteRegistrar {
    private static final Logger logger = LoggerFactory.getLogger(OrderLineController.class);
    private final OrderLineService orderLineService;
    private final OrderLineProductPartService orderLineProductPartService;

    public OrderLineController(OrderLineService orderLineService,
            OrderLineProductPartService orderLineProductPartService) {
        this.orderLineService = orderLineService;
        this.orderLineProductPartService = orderLineProductPartService;
    }

    @Override
    public void registerRoutes(RoutingHandler router) {
        router.add(Methods.GET, "/orders/{id}/orderlines", this::getOrderLinesById);
    }

    public void getOrderLinesById(HttpServerExchange exchange) {
        logger.info("Received request: GET /orders/{id}/orderlines");
        Long orderId = RequestUtils.getRequestParam(exchange, "id");
        if (orderId == null) {
            logger.warn("Invalid or missing order ID");
            JsonResponseUtil.sendErrorResponse(exchange, StatusCodes.BAD_REQUEST, "Invalid or missing order ID");
            return;
        }
        List<OrderLineDTO> orderLines = orderLineService.getByOrderId(orderId);
        JsonResponseUtil.sendJsonResponse(exchange, orderLines);
        logger.info("Responded with {} order lines", orderLines.size());

    }

    public void getProductPartsById(HttpServerExchange exchange) {
        logger.info("Received request: GET /orderlines/{id}/productparts");
        Long orderLineId = RequestUtils.getRequestParam(exchange, "id");
        if (orderLineId == null) {
            logger.warn("Invalid or missing orderLine ID");
            JsonResponseUtil.sendErrorResponse(exchange, StatusCodes.BAD_REQUEST, "Invalid or missing orderLine ID");
            return;
        }

        List<OrderLineProductPartDTO> productParts = orderLineProductPartService.getByOrderLineId(orderLineId);
        if (productParts.isEmpty()) {
            logger.warn("No product parts found for OrderLine ID {}", orderLineId);
            JsonResponseUtil.sendErrorResponse(exchange, 404, "No product parts found");
            return;
        }

        logger.info("Returning {} product parts for OrderLine ID {}", productParts.size(), orderLineId);
        JsonResponseUtil.sendJsonResponse(exchange, productParts);
    }

}
