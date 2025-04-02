package com.marcusbike.marcus_bike_api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcusbike.marcus_bike_api.dto.UserDTO;
import com.marcusbike.marcus_bike_api.services.UserService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDTO> getMethodName() {
        return userService.findAll();
    }

}
