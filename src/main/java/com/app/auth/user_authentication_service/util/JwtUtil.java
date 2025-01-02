package com.app.auth.user_authentication_service.util;

import java.security.Key;
import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoder;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


@Component
public class JwtUtil {

    private String Sectret_Key = "eyJhbGciOiJIUzI1NiJ9ew0KICAic3ViIjogIjEyMzQ1Njc4OTAiLA0KICAibmFtZSI6ICJBbmlzaCBOYXRoIiwNCiAgImlhdCI6IDE1MTYyMzkwMjINCn0NEPxm1RZ4SEpEOyDBoDZZ5jIqHbL2dxahBdTloKdjU"; // Example of 256-bit key

    public String generateToken(Map<String, Object> claims,String username) {

        return Jwts.builder()
        .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();

    }
    
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(Sectret_Key);
        return Keys.hmacShaKeyFor(keyBytes);
       
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