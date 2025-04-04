package com.marcusbike.marcus_bike_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.marcusbike.marcus_bike_api.security.JwtProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class MarcusBikeApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarcusBikeApiApplication.class, args);
	}

}
