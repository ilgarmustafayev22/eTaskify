package com.etaskify.etaskify.service;


import com.etaskify.etaskify.model.enums.TokenType;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.function.Function;

public interface JwtService {

    String extractUsername(String token);

    Claims extractAllClaims(String token);

    <T> T extractClaim(String token, Function<Claims, T> claimResolver);

    String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            TokenType tokenType
    );

    String generateToken(UserDetails userDetails, TokenType tokenType);

    boolean isTokenExpired(String token);

    boolean isTokenValid(String token, UserDetails userDetails);

}
