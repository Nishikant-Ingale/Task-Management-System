package com.nishikant.user_service.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class JwtUtil {
    private final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 Hour

    @Value("${jwt.secret}")
    private String secret;

    private Key getSigningKey(){
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
//        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String username, List<String> roles) {
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(),SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try{
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        }
        catch(JwtException e){
            log.error("Token validation Failed: {}: {}", token, e.getMessage());
            return false;
        }
    }

    public List<String> extractRoles(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("roles", List.class);
    }

    @PostConstruct
    public void init() {
        log.info("Raw secret from config: '{}'", secret); // Log exactly what's received

        try {
            byte[] decoded = Base64.getDecoder().decode(secret);
            log.info("Decoded JWT secret key length: {}", decoded.length);
        } catch (IllegalArgumentException e) {
            log.error("Base64 decoding failed: {}", e.getMessage(), e);
            throw e; // This causes the app to fail — as we want
        }
    }
}
