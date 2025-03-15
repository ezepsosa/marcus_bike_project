package com.ezepsosa.marcusbike.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezepsosa.marcusbike.dto.ProductPartDTO;
import com.ezepsosa.marcusbike.dto.ProductPartInsertDTO;
import com.ezepsosa.marcusbike.dto.ProductPartInsertProductRelationDTO;
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
        router.add(Methods.POST, "/productparts", this::insert);
        router.add(Methods.DELETE, "/productparts/{id}", this::delete);
        router.add(Methods.GET, "/products/{id}/productparts", this::getAllByProduct);
        router.add(Methods.POST, "/products/{id}/productparts", this::addRelationWithProduct);
        router.add(Methods.DELETE, "/products/{productId}/productparts/{id}", this::deleteFromProduct);

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

    public void delete(HttpServerExchange exchange) {
        logger.info("Received request: DELETE /productparts/{id}");

        Long productPartId = RequestUtils.getRequestParam(exchange, "id");

        if (productPartId == null) {
            logger.warn("Invalid or missing product part ID");
            JsonResponseUtil.sendErrorResponse(exchange, 400, "Invalid or missing product ID");
        }
        logger.info("Deleting product part with ID {}", productPartId);

        Boolean deleted = productPartService.delete(productPartId);
        if (deleted == false) {
            logger.warn("Product part with ID {} not deleted or not found", productPartId);
            JsonResponseUtil.sendErrorResponse(exchange, 404, "Product part not deleted or not found");
        }
        logger.info("Product part with ID {} found", productPartId);
        JsonResponseUtil.sendJsonResponse(exchange, "Succesfully deleted", 204);
    }

    public void deleteFromProduct(HttpServerExchange exchange) {
        logger.info("Received request: DELETE /products/{id}/productparts");

        Long productId = RequestUtils.getRequestParam(exchange, "productId");
        Long productPartId = RequestUtils.getRequestParam(exchange, "id");

        if (productPartId == null) {
            logger.warn("Invalid or missing product part ID");
            JsonResponseUtil.sendErrorResponse(exchange, 400, "Invalid or missing product part ID");
        }
        logger.info("Deleting product part with ID {}", productPartId);
        if (productId == null) {
            logger.warn("Invalid or missing product ID");
            JsonResponseUtil.sendErrorResponse(exchange, 400, "Invalid or missing product ID");
        }
        logger.info("Deleting product part with ID {}", productPartId);
        Boolean deleted = productPartService.deleteFromProduct(productId, productPartId);
        if (deleted == false) {
            logger.warn("Product part or product with IDs {},{} not deleted or not found", productPartId, productId);
            JsonResponseUtil.sendErrorResponse(exchange, 404, "Product part not deleted or not found");
        }
        logger.info("Product {} with product part ID {} deleted", productPartId, productPartId);
        JsonResponseUtil.sendJsonResponse(exchange, "Succesfully deleted", 204);
    }

    public void insert(HttpServerExchange exchange) {
        logger.info("Received request: POST /productparts");

        exchange.getRequestReceiver().receiveFullBytes((ex, message) -> {
            try {
                ProductPartInsertDTO productPartToInsert = JsonResponseUtil.parseJson(message,
                        ProductPartInsertDTO.class);

                Long productPartId = productPartService.insert(productPartToInsert);
                if (productPartId == null) {
                    logger.error("Error inserting product part");
                    JsonResponseUtil.sendErrorResponse(exchange, 500, "Failed to insert product part");
                    return;
                }
                logger.info("Product part created with ID {}", productPartId);
                ProductPartDTO productPartInserted = new ProductPartDTO(productPartId, productPartToInsert.partOption(),
                        productPartToInsert.basePrice(), productPartToInsert.isAvailable(),
                        productPartToInsert.productPartCategory());
                JsonResponseUtil.sendJsonResponse(exchange, productPartInserted, 201);

            } catch (Exception e) {
                logger.error("Error processing request", e);
                JsonResponseUtil.sendErrorResponse(exchange, 500, "Invalid request body");
            }
        });

    }

    public void addRelationWithProduct(HttpServerExchange exchange) {
        logger.info("Received request: POST /products/{id}/productparts");

        exchange.getRequestReceiver().receiveFullBytes((ex, message) -> {
            try {
                ProductPartInsertProductRelationDTO productPartToInsert = JsonResponseUtil.parseJson(message,
                        ProductPartInsertProductRelationDTO.class);

                Boolean inserted = productPartService.addRelationWithProduct(productPartToInsert);
                if (inserted == null) {
                    logger.error("error relating product to its part");
                    JsonResponseUtil.sendErrorResponse(exchange, 500, "Failed relating product to its part");
                    return;
                }
                logger.info("Successfully inserted relations");
                JsonResponseUtil.sendJsonResponse(exchange, 201);

            } catch (Exception e) {
                logger.error("Error processing request", e);
                JsonResponseUtil.sendErrorResponse(exchange, 500, "Invalid request body");
            }
        });

    }
}