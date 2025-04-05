package com.marcusbike.marcus_bike_api.mappers;

import com.marcusbike.marcus_bike_api.dto.response.UserResponseDTO;
import com.marcusbike.marcus_bike_api.models.User;

public class UserMapper {

    public static UserResponseDTO toDTO(User user) {
        return UserResponseDTO.builder().id(user.getId()).username(user.getUsername()).password(user.getPassword())
                .role(user.getRole()).build();

    }

}
