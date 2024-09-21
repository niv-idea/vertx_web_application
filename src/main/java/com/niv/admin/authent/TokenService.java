package com.niv.admin.authent;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.nio.charset.StandardCharsets;

import java.time.Instant;

import java.time.temporal.ChronoUnit;
import java.util.Date;
public enum TokenService {
    INSTANCE;

    private static final Logger log = LoggerFactory.getLogger(TokenService.class);

    // Secret key used to sign the JWT, fetched from configuration
    byte[] secret = "your-secret-key".getBytes(StandardCharsets.UTF_8); // Simplified secret key

    // Method to generate a token for a given user
    public String generateToken(String userName, Integer id) {
        try {
            // Create JWT with expiration, userName, and id claims
            return JWT.create()
                    .withExpiresAt(Date.from(Instant.now().plus(5, ChronoUnit.MINUTES))) // Token expires in 5 minutes
                    .withClaim("userName", userName)  // Add userName claim
                    .withClaim("id", id)              // Add user ID claim
                    .sign(Algorithm.HMAC256(secret)); // Sign the token using HMAC256 and secret key
        } catch (Exception e) {
            log.error("Error in generating token: {}", e.getMessage());
            return null;
        }
    }

    // Method to decode (verify) the JWT token and retrieve its claims
    public DecodedJWT decodeToken(String token) {
        try {
            // Verify and decode the token using the same signing algorithm and secret key
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
            return verifier.verify(token);  // Decode the token
        } catch (JWTVerificationException e) {
            log.error("Token verification failed: {}", e.getMessage());
            throw new IllegalArgumentException("Invalid token");
        }
    }

    // Get user ID from the decoded token
    public Integer getIdFromToken(String accessToken) {
        return decodeToken(accessToken).getClaim("id").asInt();  // Extract user ID from token
    }

    // Get userName from the decoded token
    public String getUserNameFromToken(String accessToken) {
        return decodeToken(accessToken).getClaim("userName").asString();  // Extract userName from token
    }
}

