package com.ezepsosa.marcusbike.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezepsosa.marcusbike.dto.ProductPartDTO;
import com.ezepsosa.marcusbike.routes.RouteRegistrar;
import com.ezepsosa.marcusbike.services.ProductPartService;
import com.ezepsosa.marcusbike.utils.JsonResponseUtil;
import com.ezepsosa.marcusbike.utils.RequestUtils;

import io.undertow.server.HttpServerExchange;
import io.undertow.server.RoutingHandler;
import io.undertow.util.Methods;

public class ProductPartController implements RouteRegistrar {

    private final ProductPartService productPartService;
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    public ProductPartController(ProductPartService productPartService) {
        this.productPartService = productPartService;
    }

    @Override
    public void registerRoutes(RoutingHandler router) {
        router.add(Methods.GET, "/productparts", this::getAll);
        router.add(Methods.POST, "/productparts", this::toImplement);
        router.add(Methods.DELETE, "/productparts/{id}", this::toImplement);
        router.add(Methods.GET, "/products/{id}/productparts", this::getAllByProduct);
        router.add(Methods.POST, "/products/{id}/productparts", this::toImplement);
        router.add(Methods.DELETE, "/products/{id}/productparts", this::toImplement);

    }

    public void toImplement(HttpServerExchange exchange) {

    }

    public void getAll(HttpServerExchange exchange) {
        logger.info("Received request: GET /productparts");
        List<ProductPartDTO> product = productPartService.getAll();
        JsonResponseUtil.sendJsonResponse(exchange, product);
        logger.info("Responded with {} products", product.size());

    }

    public void getAllByProduct(HttpServerExchange exchange) {
        logger.info("Received request: GET /products/{id}/productparts");
        Long productId = RequestUtils.getRequestParam(exchange, "id");
        if (productId == null) {
            logger.warn("Invalid or missing product ID");
            JsonResponseUtil.sendErrorResponse(exchange, 400, "Invalid or missing product ID");
            return;
        }
        List<ProductPartDTO> productParts = productPartService.getByProductId(productId);
        if (productParts == null) {
            logger.warn("No product parts found with ID {}", productId);
            JsonResponseUtil.sendErrorResponse(exchange, 400, "No product parts found");
            return;
        }
        JsonResponseUtil.sendJsonResponse(exchange, productParts);
        logger.info("Responded with {} product parts", productParts);

    }
}