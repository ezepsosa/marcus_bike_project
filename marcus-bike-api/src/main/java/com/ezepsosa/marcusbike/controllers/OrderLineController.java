package com.ezepsosa.marcusbike.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezepsosa.marcusbike.models.OrderLine;
import com.ezepsosa.marcusbike.routes.RouteRegistrar;
import com.ezepsosa.marcusbike.services.OrderLineService;
import com.ezepsosa.marcusbike.utils.JsonResponseUtil;

import io.undertow.server.HttpServerExchange;
import io.undertow.server.RoutingHandler;
import io.undertow.util.Methods;

public class OrderLineController implements RouteRegistrar {

    private OrderLineService orderLineService;
    private Logger logger = LoggerFactory.getLogger(OrderLineController.class);

    public OrderLineController(OrderLineService orderLineService) {
        this.orderLineService = orderLineService;
    }

    @Override
    public void registerRoutes(RoutingHandler router) {
        router.add(Methods.GET, "/orderlines", this::getAll);
    }

    public void getAll(HttpServerExchange exchange) {
        logger.info("Received request: GET /orderlines");
        List<OrderLine> orderLines = orderLineService.getAll();
        JsonResponseUtil.sendJsonResponse(exchange, orderLines);
        logger.info("Responded with {} orderlines", orderLines.size());

    }
}
