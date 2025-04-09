package com.marcusbike.marcus_bike_api.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.marcusbike.marcus_bike_api.dto.response.UserResponseDTO;
import com.marcusbike.marcus_bike_api.mappers.UserMapper;
import com.marcusbike.marcus_bike_api.models.Role;
import com.marcusbike.marcus_bike_api.models.User;
import com.marcusbike.marcus_bike_api.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

	@Mock
	private UserRepository userRepository;

	@Mock
	private UserMapper userMapper;

	@InjectMocks
	private UserService userService;

	@Test
	public void shouldReturnAllUsers() {
		String password = "password";
		Role role = Role.USER;
		User user1 = User.builder().id(1L).email("email@test.com").username("username").password(password).role(role)
				.build();
		
		User user2 = User.builder().id(2L).email("email2@test.com").username("username2").password(password).role(role)
				.build();
		
		UserResponseDTO userDTO1 = UserResponseDTO.builder().id(1L).email("email@test.com").username("username").password(password).role(role.name())
				.build();
		
		UserResponseDTO userDTO2 = UserResponseDTO.builder().id(2L).email("email2@test.com").username("username2").password(password).role(role.name())
				.build();

		List<User> users = List.of(user1, user2);
		
		when(userRepository.findAll()).thenReturn(users);

		List<UserResponseDTO> response = userService.findAll();
		assertEquals(2, response.size());
		assertEquals(response.get(0), userDTO1);
		assertEquals(response.get(1), userDTO2);
	}
	
	@Test
	public void shouldReturnUserById() {
		String password = "password";
		Role role = Role.USER;
		User user1 = User.builder().id(1L).email("email@test.com").username("username").password(password).role(role)
				.build();
		
		UserResponseDTO userDTO1 = UserResponseDTO.builder().id(1L).email("email@test.com").username("username").password(password).role(role.name())
				.build();
		
		when(userRepository.findByEmail(user1.getEmail())).thenReturn(Optional.of(user1));

		UserResponseDTO response = userService.findByEmail(user1.getEmail());
		assertNotNull(response);
		assertEquals(response.getId(), userDTO1.getId());
		assertEquals(response.getEmail(), userDTO1.getEmail());
		assertEquals(response.getPassword(), userDTO1.getPassword());
		assertEquals(response.getUsername(), userDTO1.getUsername());
		assertEquals(response.getRole(), userDTO1.getRole());
	}

}
