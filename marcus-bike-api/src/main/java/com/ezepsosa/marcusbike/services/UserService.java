package com.ezepsosa.marcusbike.services;

import java.util.List;

import com.ezepsosa.marcusbike.models.User;
import com.ezepsosa.marcusbike.repositories.UserDAO;

public class UserService {

    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public List<User> getAll() {
        return userDAO.getAll();
    }

    public User getById(Long id) {
        return userDAO.getById(id);
    }

    public Long insert(User user) {
        return userDAO.insert(user);
    }

    public boolean update(User user) {
        return userDAO.update(user);
    }

    public boolean delete(Long id) {
        return userDAO.delete(id);
    }

}
