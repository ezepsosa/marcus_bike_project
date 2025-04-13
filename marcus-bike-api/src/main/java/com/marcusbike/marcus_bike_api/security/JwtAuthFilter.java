package com.marcusbike.marcus_bike_api.security;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.marcusbike.marcus_bike_api.exceptions.JwtInvalidException;
import com.marcusbike.marcus_bike_api.repositories.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

	private final JwtService jwtService;
	private final UserRepository userRepository;

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain) throws ServletException, IOException {
		if (request.getCookies() == null) {
			filterChain.doFilter(request, response);
			return;
		}
		String token = Arrays.stream(request.getCookies()).filter(cookie -> "access_token".equals(cookie.getName()))
				.map(Cookie::getValue).findFirst().orElse(null);
		if (token != null) {
			if (!jwtService.isTokenValid(token)) {
				response.setStatus(HttpStatus.FORBIDDEN.value());
				response.setContentType("application/json");
				response.getWriter().write("{\"error\":\" Token invalid or expired \"}");
				response.getWriter().flush();
				return;

			}
			String email = jwtService.extractEmail(token);
			com.marcusbike.marcus_bike_api.models.User user = userRepository.findByEmail(email).orElse(null);
			if (token != null && user != null) {
				UserDetails userDetails = User.withUsername(user.getEmail()).password(user.getPassword())
						.authorities(user.getRole().name()).build();
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		filterChain.doFilter(request, response);
	}

}
