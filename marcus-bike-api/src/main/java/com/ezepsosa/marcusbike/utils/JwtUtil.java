package com.ezepsosa.marcusbike.utils;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

// Utility class for handling JWT generation and validation.  
// In a production environment, token expiration should be implemented for security reasons.
public class JwtUtil {

    // WARNING: The secret key is hardcoded for testing purposes only.
    // In a production environment, store it securely and never expose it in the
    // code.
    private static final String SECRET_KEY = "6e1df9684bf429b289823deec9c8ecfe5acb959141837086e827e6ded76c218a";

    private static final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    // Generates a JWT token containing the username and role.
    // No expiration is set as this is a non-production environment.
    public static String generateToken(String username, String role) {
        return Jwts.builder().subject(username).claim("role", role).issuedAt(new Date()).signWith(key).compact();
    }

    // Extracts the user role from a JWT token.
    public static String getRoleFromToken(String token) {
        return Jwts.parser().verifyWith((SecretKey) key).build().parseSignedClaims(token).getPayload().get("role",
                String.class);
    }

    // Validates a JWT token, ensuring it has a correct signature.
    public static boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith((SecretKey) key).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
