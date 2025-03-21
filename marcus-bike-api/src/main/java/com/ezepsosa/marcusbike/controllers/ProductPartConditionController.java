package com.ezepsosa.marcusbike.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezepsosa.marcusbike.dto.ProductPartConditionDTO;
import com.ezepsosa.marcusbike.dto.ProductPartConditionInsertDTO;
import com.ezepsosa.marcusbike.routes.RouteRegistrar;
import com.ezepsosa.marcusbike.security.JwtAuthHandler;
import com.ezepsosa.marcusbike.services.ProductPartConditionService;
import com.ezepsosa.marcusbike.utils.JsonResponseUtil;
import com.ezepsosa.marcusbike.utils.RequestUtils;

import io.undertow.server.HttpServerExchange;
import io.undertow.server.RoutingHandler;
import io.undertow.util.Methods;
import io.undertow.util.StatusCodes;

// Controller that handles product part condition-related API requests.
public class ProductPartConditionController implements RouteRegistrar {

    private final ProductPartConditionService productPartConditionService;
    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    public ProductPartConditionController(ProductPartConditionService productPartConditionService) {
        this.productPartConditionService = productPartConditionService;
    }

    @Override
    public void registerRoutes(RoutingHandler router) {
        router.add(Methods.GET, "/productpartconditions", this::getAll);
        router.add(Methods.POST, "/productpartconditions", new JwtAuthHandler(this::insert, List.of("ADMIN")));
        router.add(Methods.DELETE, "/productpartconditions/{productpartid}/{dependantproductpartid}",
                new JwtAuthHandler(this::delete, List.of("ADMIN")));
    }

    // Deletes a product part condition by its IDs
    public void getAll(HttpServerExchange exchange) {
        logger.info("Received request: GET /productpartconditions");
        List<ProductPartConditionDTO> productPartConditions = productPartConditionService.getAll();
        JsonResponseUtil.sendJsonResponse(exchange, productPartConditions);
        logger.info("Responded with {} products", productPartConditions.size());

    }

    // Inserts a new product part condition
    public void delete(HttpServerExchange exchange) {
        logger.info("Received request: DELETE /products/{id}");

        Long productpartid = RequestUtils.getRequestParam(exchange, "productpartid");
        Long dependantproductpartid = RequestUtils.getRequestParam(exchange, "dependantproductpartid");

        if (productpartid == null || dependantproductpartid == null) {
            logger.warn("Invalid or missing ids ID");
            JsonResponseUtil.sendErrorResponse(exchange, StatusCodes.BAD_REQUEST, "Invalid or missing product ID");
        }
        logger.info("Deleting product part condition with IDs {},{}", productpartid, dependantproductpartid);

        Boolean deleted = productPartConditionService.delete(productpartid, dependantproductpartid);
        if (deleted == false) {
            logger.warn("Product part conditions {},{} not deleted", productpartid, dependantproductpartid);
            JsonResponseUtil.sendErrorResponse(exchange, 404, "Product not deleted or not found");
        }
        logger.info("Product part condition not found", productpartid);
        JsonResponseUtil.sendJsonResponse(exchange, "Succesfully deleted", StatusCodes.NO_CONTENT);
    }

    public void insert(HttpServerExchange exchange) {
        logger.info("Received request: POST /products");

        exchange.getRequestReceiver().receiveFullBytes((ex, message) -> {
            try {
                ProductPartConditionInsertDTO productPartConditionToInsert = JsonResponseUtil.parseJson(message,
                        ProductPartConditionInsertDTO.class);

                if (productPartConditionToInsert.partId() == null
                        || productPartConditionToInsert.dependantPartId() == null) {
                    logger.error("Error validating condition");
                    JsonResponseUtil.sendErrorResponse(exchange, StatusCodes.BAD_REQUEST,
                            "Invalid condition format or missing fields");
                    return;
                }
                Boolean inserted = productPartConditionService.insert(productPartConditionToInsert);
                if (inserted == false) {
                    logger.error("Error inserting condition");
                    JsonResponseUtil.sendErrorResponse(exchange, StatusCodes.INTERNAL_SERVER_ERROR,
                            "Failed to insert condition");
                    return;
                }
                logger.info("Condition created", productPartConditionToInsert);
                JsonResponseUtil.sendJsonResponse(exchange, productPartConditionToInsert, 201);

            } catch (Exception e) {
                logger.error("Error processing request", e);
                JsonResponseUtil.sendErrorResponse(exchange, StatusCodes.INTERNAL_SERVER_ERROR, "Invalid request body");
            }
        });

    }

}
