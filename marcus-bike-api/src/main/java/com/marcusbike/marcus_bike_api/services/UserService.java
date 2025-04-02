package com.marcusbike.marcus_bike_api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.marcusbike.marcus_bike_api.models.User;
import com.marcusbike.marcus_bike_api.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

}
