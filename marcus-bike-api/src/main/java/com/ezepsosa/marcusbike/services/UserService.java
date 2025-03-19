package com.ezepsosa.marcusbike.services;

import java.util.List;
import java.util.stream.Collectors;

import com.ezepsosa.marcusbike.dto.UserDTO;
import com.ezepsosa.marcusbike.dto.UserInsertDTO;
import com.ezepsosa.marcusbike.mappers.UserMapper;
import com.ezepsosa.marcusbike.models.User;
import com.ezepsosa.marcusbike.repositories.UserDAO;
import com.ezepsosa.marcusbike.utils.TransactionHandler;

public class UserService {

    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public List<UserDTO> getAll() {
        return TransactionHandler.startTransaction((connection) -> {
            return userDAO.getAll(connection).stream().map(UserMapper::toDTO).collect(Collectors.toList());
        });
    }

    public UserDTO getById(Long id) {
        return TransactionHandler.startTransaction((connection) -> {
            return UserMapper.toDTO(userDAO.getById(connection, id));
        });
    }

    public Long insert(UserInsertDTO userToInsert) {
        return TransactionHandler.startTransaction((connection) -> {
            return userDAO.insert(connection, UserMapper.toModel(userToInsert));
        });
    }

    public boolean update(UserInsertDTO userToUpdate, long id) {
        return TransactionHandler.startTransaction((connection) -> {
            User user = UserMapper.toModel(userToUpdate);
            user.setId(id);
            return userDAO.update(connection, user);
        });

    }

    public boolean delete(Long id) {
        return TransactionHandler.startTransaction((connection) -> {
            return userDAO.delete(connection, id);
        });

    }

    public UserDTO getUserByUsernamePassword(String username, String password) {
        return TransactionHandler.startTransaction((connection) -> {
            User user = userDAO.getUserByUsernamePassword(connection, username, password);
            if (user != null) {
                return UserMapper.toDTO(user);
            } else {
                return null;
            }
        });
    }

}
