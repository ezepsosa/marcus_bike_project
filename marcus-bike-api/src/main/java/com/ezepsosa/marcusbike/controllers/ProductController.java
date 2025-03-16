package com.ezepsosa.marcusbike.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezepsosa.marcusbike.dto.ProductDTO;
import com.ezepsosa.marcusbike.dto.ProductInsertDTO;
import com.ezepsosa.marcusbike.routes.RouteRegistrar;
import com.ezepsosa.marcusbike.services.ProductService;
import com.ezepsosa.marcusbike.utils.JsonResponseUtil;
import com.ezepsosa.marcusbike.utils.RequestUtils;
import com.ezepsosa.marcusbike.utils.ValidatorUtils;

import io.undertow.server.HttpServerExchange;
import io.undertow.server.RoutingHandler;
import io.undertow.util.Methods;

public class ProductController implements RouteRegistrar {

    private final ProductService productService;
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void registerRoutes(RoutingHandler router) {
        router.add(Methods.GET, "/products", this::getAll);
        router.add(Methods.GET, "/products/{id}", this::getById);
        router.add(Methods.POST, "/products", this::insert);
        router.add(Methods.PUT, "/products/{id}", this::update);
        router.add(Methods.DELETE, "/products/{id}", this::delete);

    }

    public void getAll(HttpServerExchange exchange) {
        logger.info("Received request: GET /products");
        List<ProductDTO> product = productService.getAll();
        JsonResponseUtil.sendJsonResponse(exchange, product);
        logger.info("Responded with {} products", product.size());

    }

    public void getById(HttpServerExchange exchange) {
        logger.info("Received request: GET /products/{id}");

        Long productId = RequestUtils.getRequestParam(exchange, "id");
        if (productId == null) {
            logger.warn("Invalid or missing product ID");
            JsonResponseUtil.sendErrorResponse(exchange, 400, "Invalid or missing product ID");
            return;
        }
        logger.info("Fetching product with ID {}", productId);
        ProductDTO product = productService.getById(productId);
        if (product == null) {
            logger.warn("Product with ID {} not found", productId);
            JsonResponseUtil.sendErrorResponse(exchange, 404, "Product not found");
            return;
        }
        logger.info("Product with ID {} found", productId);
        JsonResponseUtil.sendJsonResponse(exchange, product);
    }

    public void insert(HttpServerExchange exchange) {
        logger.info("Received request: POST /products");

        exchange.getRequestReceiver().receiveFullBytes((ex, message) -> {
            try {
                ProductInsertDTO productToInsert = JsonResponseUtil.parseJson(message, ProductInsertDTO.class);

                if (!ValidatorUtils.validateProduct(productToInsert)) {
                    logger.error("Error validating product");
                    JsonResponseUtil.sendErrorResponse(exchange, 400, "Invalid product format or missing fields");
                    return;
                }
                Long productId = productService.insert(productToInsert);
                if (productId == null) {
                    logger.error("Error inserting product");
                    JsonResponseUtil.sendErrorResponse(exchange, 500, "Failed to insert product");
                    return;
                }
                logger.info("Product created with ID {}", productId);
                ProductDTO productInserted = new ProductDTO(productId, productToInsert.productName(),
                        productToInsert.brand(), productToInsert.category(), productToInsert.material(),
                        productToInsert.imageUr());
                JsonResponseUtil.sendJsonResponse(exchange, productInserted, 201);

            } catch (Exception e) {
                logger.error("Error processing request", e);
                JsonResponseUtil.sendErrorResponse(exchange, 500, "Invalid request body");
            }
        });

    }

    public void update(HttpServerExchange exchage) {
        logger.info("Received request: PUT /products/{id}");

        exchage.getRequestReceiver().receiveFullBytes((exchange, message) -> {

            try {
                ProductInsertDTO productToUpdate = JsonResponseUtil.parseJson(message, ProductInsertDTO.class);
                Long productId = RequestUtils.getRequestParam(exchange, "id");
                if (productId == null || productId < 0) {
                    logger.warn("Invalid or missing product ID");
                    JsonResponseUtil.sendErrorResponse(exchange, 400, "Invalid or missing product ID");
                    return;
                }
                if (!ValidatorUtils.validateProduct(productToUpdate)) {
                    logger.error("Error validating product");
                    JsonResponseUtil.sendErrorResponse(exchange, 400, "Invalid product format or missing fields");
                    return;
                }
                Boolean updated = productService.update(productToUpdate, productId);
                if (!updated) {
                    logger.error("Error updating product");
                    JsonResponseUtil.sendErrorResponse(exchange, 204, "Product not updated");
                    return;
                }
                logger.info("Product updated with ID {}", productId);
                ProductDTO product = new ProductDTO(productId, productToUpdate.productName(),
                        productToUpdate.brand(), productToUpdate.category(), productToUpdate.material(),
                        productToUpdate.imageUr());
                JsonResponseUtil.sendJsonResponse(exchange, product);
            } catch (Exception e) {
                logger.error("Error processing request", e);
                JsonResponseUtil.sendErrorResponse(exchange, 500, "Invalid request body");
            }
        });
    }

    public void delete(HttpServerExchange exchange) {
        logger.info("Received request: DELETE /products/{id}");

        Long productId = RequestUtils.getRequestParam(exchange, "id");
        if (productId == null) {
            logger.warn("Invalid or missing product ID");
            JsonResponseUtil.sendErrorResponse(exchange, 400, "Invalid or missing product ID");
        }
        logger.info("Deleting product with ID {}", productId);

        Boolean deleted = productService.delete(productId);
        if (deleted == false) {
            logger.warn("Product with ID {} not deleted or not found", productId);
            JsonResponseUtil.sendErrorResponse(exchange, 404, "Product not deleted or not found");
        }
        logger.info("Product with ID {} found", productId);
        JsonResponseUtil.sendJsonResponse(exchange, "Succesfully deleted", 204);
    }
}
