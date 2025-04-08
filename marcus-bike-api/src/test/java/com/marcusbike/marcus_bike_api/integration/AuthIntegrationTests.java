package com.marcusbike.marcus_bike_api.integration;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcusbike.marcus_bike_api.dto.request.AuthRequest;
import com.marcusbike.marcus_bike_api.dto.request.UserInsertDTO;
import com.marcusbike.marcus_bike_api.models.User;
import com.marcusbike.marcus_bike_api.repositories.UserRepository;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class AuthIntegrationTests {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
    @Autowired
    private Flyway flyway;
	
    @BeforeEach
    void resetDatabase() {
        flyway.clean();
        flyway.migrate();
    }
	
	@Test
	void shouldRegisterUserSuccessfully() throws Exception {
		String email = "email@test.com";
		String password = "Password123#;";
		String username = "username";
		String role = "USER";
		
		UserInsertDTO userDTO = UserInsertDTO.builder().email(email).username(username).password(password).role(role).build();
		String body = new ObjectMapper().writeValueAsString(userDTO);
		mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON).content(body)).andExpect(status().isCreated());
		
		User user = userRepository.findByEmail(email).orElseThrow();
		assertNotNull(user);
		assertEquals(email, user.getEmail());
		assertEquals(username, user.getUsername());
		assertEquals(role, user.getRole().name());
		assertTrue(passwordEncoder.matches(password, user.getPassword()));
	}
	
	@Test
	void shouldLoginUserSuccessfully() throws Exception {
		String email = "admin@test.com";
		String password = "password";
		AuthRequest authRequest = AuthRequest.builder().email(email).password(password).build();
		String body = new ObjectMapper().writeValueAsString(authRequest);
		
		System.out.println(userRepository.findAll());
		mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(body))
				.andExpect(status().isAccepted())
				.andExpect(header().stringValues("Set-Cookie", hasItems(containsString("access_token="),
				containsString("refresh_token="))));
	}
	
}

