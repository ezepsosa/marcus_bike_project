package com.ezepsosa.marcusbike.mappers;

import com.ezepsosa.marcusbike.dto.UserDTO;
import com.ezepsosa.marcusbike.dto.UserInsertDTO;
import com.ezepsosa.marcusbike.models.User;
import com.ezepsosa.marcusbike.models.UserRole;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRole().name());
    }

    public static User toModel(UserInsertDTO userInsertDTO) {
        return new User(userInsertDTO.username(), userInsertDTO.email(), userInsertDTO.password(),
                UserRole.USER);
    }

}
