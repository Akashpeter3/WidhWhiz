package com.app.auth.user_authentication_service.util;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

    private  String Sectret_Key = "Akash-Peter-Key";

    public String generateToken(String username) {

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, Sectret_Key)
                .compact();

    }
    
    public String extractUsername(String token) {
        return Jwts.parser().setSigningKey(Sectret_Key).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token,String username) {
        String tokenUsername = extractUsername(token);
        return (tokenUsername.equals(username) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return Jwts.parser().setSigningKey(Sectret_Key).parseClaimsJws(token).getBody().getExpiration().before(new Date());
    }
}