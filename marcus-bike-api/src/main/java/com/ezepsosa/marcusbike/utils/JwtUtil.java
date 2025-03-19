package com.ezepsosa.marcusbike.utils;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {

    private static final String SECRET_KEY = "6e1df9684bf429b289823deec9c8ecfe5acb959141837086e827e6ded76c218a";

    private static final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public static String generateToken(String subject) {
        return Jwts.builder().subject(subject).issuedAt(new Date()).signWith(key).compact();
    }

    public static String getSubjectFromToken(String token) {
        return Jwts.parser().verifyWith((SecretKey) key).build().parseSignedClaims(token).getPayload().getSubject();
    }

    public static boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith((SecretKey) key).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
