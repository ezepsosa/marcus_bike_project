package com.marcusbike.marcus_bike_api.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.marcusbike.marcus_bike_api.dto.response.UserResponseDTO;
import com.marcusbike.marcus_bike_api.mappers.UserMapper;
import com.marcusbike.marcus_bike_api.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserResponseDTO> findAll() {
        return userRepository.findAll().stream().map(user -> UserMapper.toDTO(user)).collect(Collectors.toList());
    }

}
