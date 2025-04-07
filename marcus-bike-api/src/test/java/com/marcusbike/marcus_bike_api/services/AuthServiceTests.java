package com.marcusbike.marcus_bike_api.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.marcusbike.marcus_bike_api.dto.request.UserInsertDTO;
import com.marcusbike.marcus_bike_api.dto.response.AuthResponse;
import com.marcusbike.marcus_bike_api.exceptions.EmailAlreadyUsedException;
import com.marcusbike.marcus_bike_api.exceptions.InvalidCredentialsException;
import com.marcusbike.marcus_bike_api.exceptions.UsernameAlreadyUsedException;
import com.marcusbike.marcus_bike_api.models.User;
import com.marcusbike.marcus_bike_api.repositories.UserRepository;
import com.marcusbike.marcus_bike_api.security.JwtService;

@ExtendWith(MockitoExtension.class)
class MarcusBikeApiApplicationTests {

	@Mock
	private AuthenticationManager authenticationManager;

	@Mock
	private JwtService jwtService;

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private AuthService authService;

	@Test
	void whenLoginIsCorrectReturnToken() {
		String email = "john@doe.com";
		String password = "password";
		String token = "token";
		String refreshToken = "refreshToken";

		when(jwtService.generateToken(email)).thenReturn(token);
		when(jwtService.generateRefreshToken(email)).thenReturn(refreshToken);

		AuthResponse response = authService.login(email, password);
		assertEquals(token, response.getToken());
		assertEquals(refreshToken, response.getRefreshToken());
	}

	@Test
	void whenLoginIsIncorrectThrowInvalidCredentiaslException() {
		String email = "john@doe.com";
		String password = "password";

		when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password)))
				.thenThrow(new BadCredentialsException("Invalid Credentials"));

		assertThrows(InvalidCredentialsException.class, () -> {
			authService.login(email, password);
		});
	}

	@Test
	void whenRegisterEmailAlreadyInUseThrowEmailAlreadyUsedException() {
		String username = "john";
		String email = "john@doe.com";

		when(userRepository.findByEmailOrUsername(email, username))
				.thenReturn(Optional.of(User.builder().email("john@doe.com").build()));

		EmailAlreadyUsedException exception = assertThrows(EmailAlreadyUsedException.class, () -> {
			UserInsertDTO userInsert = UserInsertDTO.builder().email(email).username(username).build();
			authService.register(userInsert);
		});

		assertEquals("The email is already in use", exception.getMessage());
	}

	@Test
	void whenRegisterUsernameAlreadyInUseThrowUseranemAlreadyUsedException() {
		String username = "john";
		String email = "john@doe.com";

		when(userRepository.findByEmailOrUsername(email, username))
				.thenReturn(Optional.of(User.builder().email("NotJohn@doe.com").build()));

		UsernameAlreadyUsedException exception = assertThrows(UsernameAlreadyUsedException.class, () -> {
			UserInsertDTO userInsert = UserInsertDTO.builder().email(email).username(username).build();
			authService.register(userInsert);
		});

		assertEquals("The username is already in use", exception.getMessage());
	}

	@Test
	void whenRegisterSuccessfullReturnToken() {
		String username = "john";
		String email = "john@doe.com";
		String password = "password";
		String role = "ADMIN";
		UserInsertDTO userInsert = UserInsertDTO.builder().email(email).username(username).role(role).password(password)
				.build();
		when(userRepository.findByEmailOrUsername(email, username)).thenReturn(Optional.empty());

		authService.register(userInsert);
		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
		verify(userRepository, times(1)).save(userCaptor.capture());
		User savedUser = userCaptor.getValue();

		assertEquals(email, savedUser.getEmail());
		assertEquals(username, savedUser.getUsername());
		assertEquals(role, savedUser.getRole());
		assertNotEquals(password, savedUser.getPassword());
		assertTrue(savedUser.getPassword().startsWith("$2"));
	}

	@Test
	void whenRegisterReturnThrowValidationStep1() {
		String username = "";
		String email = "";
		String password = "";
		String role = "";
		UserInsertDTO userInsert = UserInsertDTO.builder().email(email).username(username).role(role).password(password)
				.build();
		when(userRepository.findByEmailOrUsername(email, username)).thenReturn(Optional.empty());

		authService.register(userInsert);
		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
		verify(userRepository, times(1)).save(userCaptor.capture());
		User savedUser = userCaptor.getValue();

		assertEquals(email, savedUser.getEmail());
		assertEquals(username, savedUser.getUsername());
		assertEquals(role, savedUser.getRole());
		assertNotEquals(password, savedUser.getPassword());
		assertTrue(savedUser.getPassword().startsWith("$2"));
	}

}
