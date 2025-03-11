package com.ezepsosa.marcusbike.config;

import com.ezepsosa.marcusbike.controllers.UserController;
import com.ezepsosa.marcusbike.repositories.UserDAO;
import com.ezepsosa.marcusbike.services.UserService;

public class DependencyInjection {

    private final UserDAO userdao = new UserDAO();
    private final UserService userService = new UserService(userdao);
    private final UserController userController = new UserController(userService);

    public UserController getUserController() {
        return userController;
    }

}
