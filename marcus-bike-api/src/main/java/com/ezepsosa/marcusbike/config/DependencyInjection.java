package com.ezepsosa.marcusbike.config;

import com.ezepsosa.marcusbike.controllers.OrderController;
import com.ezepsosa.marcusbike.controllers.ProductController;
import com.ezepsosa.marcusbike.controllers.UserController;
import com.ezepsosa.marcusbike.repositories.OrderDAO;
import com.ezepsosa.marcusbike.repositories.OrderLineDAO;
import com.ezepsosa.marcusbike.repositories.ProductDAO;
import com.ezepsosa.marcusbike.repositories.UserDAO;
import com.ezepsosa.marcusbike.services.OrderLineService;
import com.ezepsosa.marcusbike.services.OrderService;
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

    // Order
    private final OrderDAO orderdao = new OrderDAO();
    private final OrderService orderservice = new OrderService(orderdao);
    private final OrderController orderController = new OrderController(orderservice);

    public OrderController getOrderController() {
        return orderController;
    }

}
