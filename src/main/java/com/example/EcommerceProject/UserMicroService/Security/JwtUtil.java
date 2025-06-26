package com.example.EcommerceProject.UserMicroService.Security;

import com.example.EcommerceProject.UserMicroService.Controller.AuthController;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import javax.annotation.PostConstruct;



@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    private Key key;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostConstruct
    public void init() {
        //this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        if (secret == null || secret.isEmpty()) {
            throw new IllegalArgumentException("JWT Secret is not provided");
        }
        //System.out.println("JWT Secret: " + secret);  // Debug statement
        logger.info("JWT secret " + secret);
        key = Keys.hmacShaKeyFor(secret.getBytes());
        logger.info("Key initialized: " + (key != null));  // Check if the key is initialized

    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hour expiry
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            String username = extractUsername(token);
            return username != null && username.equals(userDetails.getUsername());
        } catch (JwtException e) {
            return false;
        }
    }
}
