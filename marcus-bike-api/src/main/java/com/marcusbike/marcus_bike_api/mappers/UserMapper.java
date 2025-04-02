package com.marcusbike.marcus_bike_api.mappers;

import com.marcusbike.marcus_bike_api.dto.UserDTO;
import com.marcusbike.marcus_bike_api.models.User;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        return UserDTO.builder().id(user.getId()).username(user.getUsername()).password(user.getPassword())
                .role(user.getRole()).build();

    }

}
