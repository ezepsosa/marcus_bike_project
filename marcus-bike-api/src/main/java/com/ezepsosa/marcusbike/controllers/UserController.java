package com.ezepsosa.marcusbike.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezepsosa.marcusbike.models.User;
import com.ezepsosa.marcusbike.routes.RouteRegistrar;
import com.ezepsosa.marcusbike.services.UserService;
import com.ezepsosa.marcusbike.utils.JsonResponseUtil;
import com.ezepsosa.marcusbike.utils.RequestUtils;
import com.ezepsosa.marcusbike.utils.ValidatorUtils;

import io.undertow.server.HttpServerExchange;
import io.undertow.server.RoutingHandler;
import io.undertow.util.Methods;

public class UserController implements RouteRegistrar {

    private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void registerRoutes(RoutingHandler router) {
        router.add(Methods.GET, "/users", this::getAll);
        router.add(Methods.GET, "/users/{id}", this::getById);
        router.add(Methods.POST, "/users", this::insert);
        router.add(Methods.PUT, "/users", this::update);
        router.add(Methods.DELETE, "/users/{id}", this::delete);

    }

    public void getAll(HttpServerExchange exchange) {
        logger.info("Received request: GET /users");
        List<User> users = userService.getAll();
        JsonResponseUtil.sendJsonResponse(exchange, users);
        logger.info("Responded with {} users", users.size());

    }

    public void getById(HttpServerExchange exchange) {
        logger.info("Received request: GET /users/{id}");

        Long userId = RequestUtils.getRequestParam(exchange, "id");
        if (userId == null) {
            logger.warn("Invalid or missing user ID");
            JsonResponseUtil.sendErrorResponse(exchange, 400, "Invalid or missing user ID");
        }
        logger.info("Fetching user with ID {}", userId);

        User user = userService.getById(userId);
        if (user == null) {
            logger.warn("User with ID {} not found", userId);
            JsonResponseUtil.sendErrorResponse(exchange, 404, "User not found");
        }
        logger.info("User with ID {} found", userId);
        JsonResponseUtil.sendJsonResponse(exchange, user);
    }

    public void insert(HttpServerExchange exchange) {
        logger.info("Received request: POST /users");

        exchange.getRequestReceiver().receiveFullBytes((ex, message) -> {
            try {
                User userToInsert = JsonResponseUtil.parseJson(message, User.class);

                if (!ValidatorUtils.validateUser(userToInsert)) {
                    logger.error("Error validating user");
                    JsonResponseUtil.sendErrorResponse(exchange, 400, "Invalid user format or missing fields");
                    return;
                }
                Long userId = userService.insert(userToInsert);
                if (userId == null) {
                    logger.error("Error inserting user");
                    JsonResponseUtil.sendErrorResponse(exchange, 500, "Failed to insert user");
                    return;
                }
                logger.info("User created with ID {}", userId);
                userToInsert.setId(userId);
                JsonResponseUtil.sendJsonResponse(exchange, userId, 201);

            } catch (Exception e) {
                logger.error("Error processing request", e);
                JsonResponseUtil.sendErrorResponse(exchange, 500, "Invalid request body");
            }
        });

    }

    public void update(HttpServerExchange exchage) {
        logger.info("Received request: PUT /users");

        exchage.getRequestReceiver().receiveFullBytes((exchange, message) -> {
            try {
                User userToUpdate = JsonResponseUtil.parseJson(message, User.class);

                if (!ValidatorUtils.validateUser(userToUpdate) && userToUpdate.getId() != null
                        && userToUpdate.getId() > 0) {
                    logger.error("Error validating user");
                    JsonResponseUtil.sendErrorResponse(exchange, 400, "Invalid user format or missing fields");
                    return;
                }
                Boolean updated = userService.update(userToUpdate);
                if (!updated) {
                    logger.error("Error updating user");
                    JsonResponseUtil.sendErrorResponse(exchange, 204, "User not updated");
                    return;
                }
                logger.info("User updated with ID {}", userToUpdate.getId());
                userToUpdate = userService.getById(userToUpdate.getId());
                JsonResponseUtil.sendJsonResponse(exchange, userToUpdate);
            } catch (Exception e) {
                logger.error("Error processing request", e);
                JsonResponseUtil.sendErrorResponse(exchange, 500, "Invalid request body");
            }
        });
    }

    public void delete(HttpServerExchange exchange) {
        logger.info("Received request: DELETE /users/{id}");

        Long userId = RequestUtils.getRequestParam(exchange, "id");
        if (userId == null) {
            logger.warn("Invalid or missing user ID");
            JsonResponseUtil.sendErrorResponse(exchange, 400, "Invalid or missing user ID");
        }
        logger.info("Deleting user with ID {}", userId);

        Boolean deleted = userService.delete(userId);
        if (deleted == false) {
            logger.warn("User with ID {} not deleted or not found", userId);
            JsonResponseUtil.sendErrorResponse(exchange, 404, "User not deleted or not found");
        }
        logger.info("User with ID {} found", userId);
        JsonResponseUtil.sendJsonResponse(exchange, "Succesfully deleted");
    }
}
