package com.ezepsosa.marcusbike.config;

import com.ezepsosa.marcusbike.controllers.OrderLineController;
import com.ezepsosa.marcusbike.controllers.ProductController;
import com.ezepsosa.marcusbike.controllers.UserController;
import com.ezepsosa.marcusbike.repositories.OrderLineDAO;
import com.ezepsosa.marcusbike.repositories.ProductDAO;
import com.ezepsosa.marcusbike.repositories.UserDAO;
import com.ezepsosa.marcusbike.services.OrderLineService;
import com.ezepsosa.marcusbike.services.ProductService;
import com.ezepsosa.marcusbike.services.UserService;

public class DependencyInjection {
    // User
    private final UserDAO userdao = new UserDAO();
    private final UserService userService = new UserService(userdao);
    private final UserController userController = new UserController(userService);

    public UserController getUserController() {
        return userController;
    }

    // Product
    private final ProductDAO productdao = new ProductDAO();
    private final ProductService productService = new ProductService(productdao);
    private final ProductController productContoller = new ProductController(productService);

    public ProductController getProductController() {
        return productContoller;
    }

    // OrderLine
    private final OrderLineDAO orderlinedao = new OrderLineDAO();
    private final OrderLineService orderlineservice = new OrderLineService(orderlinedao);
    private final OrderLineController orderlinecontroller = new OrderLineController(orderlineservice);

    public OrderLineController getOrderLineController() {
        return orderlinecontroller;
    }
}
