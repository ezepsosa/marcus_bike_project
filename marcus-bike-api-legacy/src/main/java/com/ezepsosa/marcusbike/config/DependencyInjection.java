package com.ezepsosa.marcusbike.config;

import com.ezepsosa.marcusbike.controllers.AuthController;
import com.ezepsosa.marcusbike.controllers.OrderController;
import com.ezepsosa.marcusbike.controllers.OrderLineController;
import com.ezepsosa.marcusbike.controllers.ProductController;
import com.ezepsosa.marcusbike.controllers.ProductPartConditionController;
import com.ezepsosa.marcusbike.controllers.ProductPartController;
import com.ezepsosa.marcusbike.controllers.UserController;
import com.ezepsosa.marcusbike.repositories.OrderDAO;
import com.ezepsosa.marcusbike.repositories.OrderLineDAO;
import com.ezepsosa.marcusbike.repositories.OrderLineProductPartDAO;
import com.ezepsosa.marcusbike.repositories.ProductDAO;
import com.ezepsosa.marcusbike.repositories.ProductPartConditionDAO;
import com.ezepsosa.marcusbike.repositories.ProductPartDAO;
import com.ezepsosa.marcusbike.repositories.UserDAO;
import com.ezepsosa.marcusbike.services.AuthService;
import com.ezepsosa.marcusbike.services.OrderLineProductPartService;
import com.ezepsosa.marcusbike.services.OrderLineService;
import com.ezepsosa.marcusbike.services.OrderService;
import com.ezepsosa.marcusbike.services.ProductPartConditionService;
import com.ezepsosa.marcusbike.services.ProductPartService;
import com.ezepsosa.marcusbike.services.ProductService;
import com.ezepsosa.marcusbike.services.UserService;

// Class that manually manages dependencies for DAOs, services, and controllers.
public class DependencyInjection {
    // User
    private final UserDAO userDAO = new UserDAO();
    private final UserService userService = new UserService(userDAO);
    private final UserController userController = new UserController(userService);

    public UserController getUserController() {
        return userController;
    }

    // Product
    private final ProductDAO productDAO = new ProductDAO();
    private final ProductService productService = new ProductService(productDAO);
    private final ProductController productContoller = new ProductController(productService);

    public ProductController getProductController() {
        return productContoller;
    }

    // ProductPart

    private final ProductPartDAO productPartDAO = new ProductPartDAO();
    private final ProductPartService productPartService = new ProductPartService(productPartDAO);
    private final ProductPartController productPartController = new ProductPartController(productPartService);

    public ProductPartController getProductPartController() {
        return productPartController;
    }

    // ProductPartCondition
    private final ProductPartConditionDAO productPartCondition = new ProductPartConditionDAO();
    private final ProductPartConditionService productPartConditionService = new ProductPartConditionService(
            productPartCondition);

    public ProductPartConditionController productPartConditionController = new ProductPartConditionController(
            productPartConditionService);

    public ProductPartConditionController getProductPartConditionController() {
        return productPartConditionController;
    }

    // OrderLineProductParts
    private final OrderLineProductPartDAO orderLineProductPartDAO = new OrderLineProductPartDAO();
    private final OrderLineProductPartService orderLineProductPartService = new OrderLineProductPartService(
            orderLineProductPartDAO, productPartConditionService, productPartService);

    // OrderLine
    private final OrderLineDAO orderLineDAO = new OrderLineDAO();
    private final OrderLineService orderlineService = new OrderLineService(orderLineDAO, orderLineProductPartService);
    private final OrderLineController orderLineController = new OrderLineController(orderlineService,
            orderLineProductPartService);

    public OrderLineController getOrderLineController() {
        return orderLineController;
    }

    // Order
    private final OrderDAO orderDAO = new OrderDAO();
    private final OrderService orderservice = new OrderService(orderDAO, orderlineService);
    private final OrderController orderController = new OrderController(orderservice);

    public OrderController getOrderController() {
        return orderController;
    }

    // Login

    private final AuthService authService = new AuthService();
    private final AuthController authController = new AuthController(authService, userService);

    public AuthController getAuthController() {
        return authController;
    }

}
