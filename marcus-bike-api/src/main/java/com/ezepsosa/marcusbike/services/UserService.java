package com.ezepsosa.marcusbike.services;

import java.util.List;
import java.util.stream.Collectors;

import com.ezepsosa.marcusbike.dto.UserDTO;
import com.ezepsosa.marcusbike.dto.UserInsertDTO;
import com.ezepsosa.marcusbike.mappers.UserMapper;
import com.ezepsosa.marcusbike.models.User;
import com.ezepsosa.marcusbike.repositories.UserDAO;

public class UserService {

    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public List<UserDTO> getAll() {
        return userDAO.getAll().stream().map(UserMapper::toDTO).collect(Collectors.toList());
    }

    public UserDTO getById(Long id) {
        return UserMapper.toDTO(userDAO.getById(id));
    }

    public Long insert(UserInsertDTO userToInsert) {
        return userDAO.insert(UserMapper.toModel(userToInsert));
    }

    public boolean update(UserInsertDTO userToUpdate, long id) {
        User user = UserMapper.toModel(userToUpdate);
        user.setId(id);
        return userDAO.update(user);
    }

    public boolean delete(Long id) {
        return userDAO.delete(id);
    }

}
