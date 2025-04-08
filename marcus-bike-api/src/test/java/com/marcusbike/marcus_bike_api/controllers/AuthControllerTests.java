package com.marcusbike.marcus_bike_api.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcusbike.marcus_bike_api.dto.request.UserInsertDTO;
import com.marcusbike.marcus_bike_api.dto.response.AuthResponse;
import com.marcusbike.marcus_bike_api.exceptions.EmailAlreadyUsedException;
import com.marcusbike.marcus_bike_api.exceptions.InvalidCredentialsException;
import com.marcusbike.marcus_bike_api.exceptions.UsernameAlreadyUsedException;
import com.marcusbike.marcus_bike_api.security.JwtAuthFilter;
import com.marcusbike.marcus_bike_api.security.JwtProperties;
import com.marcusbike.marcus_bike_api.services.AuthService;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private JwtProperties jwtProperties;

	@MockitoBean
	private JwtAuthFilter jwtAuthFilter;

	@MockitoBean
	private AuthService authService;

	@Test
	void loginShouldReturnCookiesAndAcceptedStatus() throws JsonProcessingException, Exception {
		String email = "john@doe.com";
		String password = "passowrd";
		String token = "token";
		String refreshToken = "refreshToken";
		Map<String, String> request = Map.of("email", email, "password", password);
		String body = new ObjectMapper().writeValueAsString(request);

		when(jwtProperties.getExpiration()).thenReturn(6000L);
		when(jwtProperties.getRefreshExpiration()).thenReturn(604800L);
		when(authService.login(email, password))
				.thenReturn(AuthResponse.builder().token(token).refreshToken(refreshToken).build());

		mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(body))
				.andExpect(status().isAccepted()).andExpect(header().exists("Set-Cookie"));
	}

	@Test
	void loginShouldReturnInvalidCredentials() throws JsonProcessingException, Exception {
		String email = "john@doe.com";
		String password = "passowrd";
		Map<String, String> request = Map.of("email", email, "password", password);
		String body = new ObjectMapper().writeValueAsString(request);

		when(authService.login(email, password)).thenThrow(new InvalidCredentialsException("Invalid credentials"));

		mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(body))
				.andExpect(status().isUnauthorized()).andExpect(jsonPath("$.error").value("Invalid credentials"));
	}

	@Test
	void registerShouldReturnCreatedStatus() throws Exception {
		String email = "john@doe.com";
		String username = "username";
		String password = "Valid#123";
		String role = "USER";
		UserInsertDTO user = UserInsertDTO.builder().email(email).password(password).role(role).username(username)
				.build();
		String body = new ObjectMapper().writeValueAsString(user);
		mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON).content(body))
				.andExpect(status().isCreated());

	}

	@Test
	void registerShouldReturnEmailExists() throws Exception {
		String email = "john@doe.com";
		String username = "username";
		String password = "Valid#123";
		String role = "USER";
		UserInsertDTO user = UserInsertDTO.builder().email(email).password(password).role(role).username(username)
				.build();
		doThrow(new EmailAlreadyUsedException("The email is already in use")).when(authService).register(user);

		String body = new ObjectMapper().writeValueAsString(user);
		mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON).content(body))
				.andExpect(status().isConflict()).andExpect(jsonPath("$.error").value("The email is already in use"));

	}

	@Test
	void registerShouldReturnUsernameExists() throws Exception {
		String email = "john@doe.com";
		String username = "username";
		String password = "Valid#123";
		String role = "USER";
		UserInsertDTO user = UserInsertDTO.builder().email(email).password(password).role(role).username(username)
				.build();
		String body = new ObjectMapper().writeValueAsString(user);
		doThrow(new UsernameAlreadyUsedException("The username is already in use")).when(authService).register(user);
		mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON).content(body))
				.andExpect(status().isConflict())
				.andExpect(jsonPath("$.error").value("The username is already in use"));

	}

	@Test
	void registerShouldFailedInSept1() throws Exception {
		String email = "";
		String username = "";
		String password = "";
		String role = "";
		UserInsertDTO user = UserInsertDTO.builder().email(email).password(password).role(role).username(username)
				.build();
		String body = new ObjectMapper().writeValueAsString(user);
		mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON).content(body))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.message").value("Validation failed"))
				.andExpect(jsonPath("$.errors").isArray()).andExpect(jsonPath("$.errors.length()").value(4))
				.andExpect(jsonPath("$.errors[?(@.field == 'email')].message").value("Email field is mandatory"))
				.andExpect(jsonPath("$.errors[?(@.field == 'password')].message").value("Password field is mandatory"))
				.andExpect(jsonPath("$.errors[?(@.field == 'role')].message").value("Role field is mandatory"))
				.andExpect(jsonPath("$.errors[?(@.field == 'username')].message").value("Username field is mandatory"));

	}

	@Test
	void registerShouldFailedInSept2() throws Exception {
		String email = "aa".repeat(245) + "@test.com";
		String username = "a".repeat(56);
		String password = "a".repeat(101);
		String role = "invalidrole";
		UserInsertDTO user = UserInsertDTO.builder().email(email).password(password).role(role).username(username)
				.build();
		String body = new ObjectMapper().writeValueAsString(user);
		mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON).content(body))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.message").value("Validation failed"))
				.andExpect(jsonPath("$.errors").isArray()).andExpect(jsonPath("$.errors.length()").value(4))
				.andExpect(jsonPath("$.errors[?(@.field == 'email')].message")
						.value("Email field can't have more than 254 characters"))
				.andExpect(jsonPath("$.errors[?(@.field == 'password')].message")
						.value("Password must have between 8 and 100 characters"))
				.andExpect(jsonPath("$.errors[?(@.field == 'role')].message").value("Role must be USER or ADMIN"))
				.andExpect(jsonPath("$.errors[?(@.field == 'username')].message")
						.value("Username field can't have more than 50 characters"));

	}

	@Test
	void registerShouldFailedInSept3() throws Exception {
		String email = "john";
		String username = "username";
		String password = "Notvalid";
		String role = "USER";
		UserInsertDTO user = UserInsertDTO.builder().email(email).password(password).role(role).username(username)
				.build();
		String body = new ObjectMapper().writeValueAsString(user);
		mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON).content(body))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.message").value("Validation failed"))
				.andExpect(jsonPath("$.errors").isArray()).andExpect(jsonPath("$.errors.length()").value(2))
				.andExpect(jsonPath("$.errors[?(@.field == 'email')].message").value("Email format is not correct"))
				.andExpect(jsonPath("$.errors[?(@.field == 'password')].message")
						.value("Password field must include uppercase, lowercase, numbers and a special character"));

	}

	@Test
	void shouldHandleUnexpectedExceptionWith500() throws Exception {
		UserInsertDTO user = UserInsertDTO.builder().email("email@example.com").username("username")
				.password("Valid#123").role("USER").build();
		doThrow(new RuntimeException("Something went wrong")).when(authService).register(any());

		String body = new ObjectMapper().writeValueAsString(user);

		mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON).content(body))
				.andExpect(status().isInternalServerError())
				.andExpect(jsonPath("$.error").value("Something went wrong"));
	}
}