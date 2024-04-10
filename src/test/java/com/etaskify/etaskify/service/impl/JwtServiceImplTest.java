package com.etaskify.etaskify.service.impl;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

// Other imports may be needed

@ExtendWith(MockitoExtension.class)
public class JwtServiceImplTest {

    @Mock
    private Claims claims;

    @InjectMocks
    private JwtServiceImpl jwtService; // Assuming we have configured @Value for the properties

    @Test
    public void testExtractUsername() {
        String expectedUsername = "testuser";
        //when(claims.getSubject()).thenReturn(expectedUsername);
        //when(jwtService.extractClaim(anyString(), any())).thenReturn(claims);

        //String result = jwtService.extractUsername("sometoken");
        //assertEquals(expectedUsername, result);
    }

}
