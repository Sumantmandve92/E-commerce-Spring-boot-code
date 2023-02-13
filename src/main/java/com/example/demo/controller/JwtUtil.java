package com.example.demo.controller;

import io.jsonwebtoken.Claims;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

	@Autowired
	UserDetails userDetails;
    private String SECRET_KEY = "dfhfhf56h465h6eh5r6f6a5f6h6f5h65hr"
    		+ "r56gr9gr65g6r5g6rg5rg5r5gr95gr9g5r9g5r9g5r6g5r9"
    		+ "g85r9g5re9g59r5g9r5g9rg59r8g59r5g9r8g9r5g9r5g9r8g9"
    		+ "r8g9r5g9r8g9r8g9r8gr95g9rg5r98gre98g9r5g9r5g9r8g9r5g95r6"
    		+ "g4r6g5r95g9rg59rg9r8g9r5gr95g9r5g9r5f9rg9r8g9r5g9r5g95r9g5r9"
    		+ "g5r9g5r9g8r9g8r9g59rg5r9g5r9g59rg5"
    		+ "9rg5r95g6r5g6r5gr5gr58g9r8g94g9r5g9";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
   

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}