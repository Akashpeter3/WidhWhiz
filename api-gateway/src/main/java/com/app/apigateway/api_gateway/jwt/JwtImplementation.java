package com.app.apigateway.api_gateway.jwt;

import org.springframework.stereotype.Component;

import java.security.Key;



import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtImplementation {

 private static final String SECRET_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.aGVsbG8gd29ybGQgd2l0aCBhIHZhbGlkIHNlY3JldCBrZXk";

    /**
     * Generates a JWT token with the specified claims and username.
     *
     * @param claims   Additional claims to include in the token
     * @param username The username for which the token is generated
     * @return A JWT token
     */


    /**
     * Retrieves the signing key used for JWT token generation and validation.
     *
     * @return The signing key
     */
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Validates a token without checking the username.
     *
     * @param token The JWT token
     */
    public void validateTokenWithoutUsernameCheck(String token) {
        Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token);
    }

}
