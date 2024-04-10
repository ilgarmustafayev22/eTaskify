package com.etaskify.etaskify.service.impl;

import com.etaskify.etaskify.dao.entity.OrganizationEntity;
import com.etaskify.etaskify.dao.entity.TaskEntity;
import com.etaskify.etaskify.dao.entity.UserEntity;
import com.etaskify.etaskify.dao.repositroy.OrganizationRepository;
import com.etaskify.etaskify.dao.repositroy.UserRepository;
import com.etaskify.etaskify.exception.UserNotFoundException;
import com.etaskify.etaskify.exception.UsernameAlreadyExistsException;
import com.etaskify.etaskify.model.TokenPair;
import com.etaskify.etaskify.model.dto.AuthenticationDto;
import com.etaskify.etaskify.model.enums.TokenType;
import com.etaskify.etaskify.model.enums.UserRole;
import com.etaskify.etaskify.model.request.SigninRequest;
import com.etaskify.etaskify.model.request.SignupRequest;
import com.etaskify.etaskify.service.JwtService;
import com.etaskify.etaskify.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {

    @Mock
    private UserRepository repository;
    @Mock
    private AuthenticationManager authManager;
    @Mock
    private UserService userService;
    @Mock
    private JwtService jwtService;
    @Mock
    private PasswordEncoder encoder;

    @Mock
    private OrganizationRepository organizationRepository;

    // We omit @Mock for authManager since you're likely already providing it elsewhere in your config

    @InjectMocks
    private AuthenticationServiceImpl authService;

    @Test
    public void signup_success() {
        String email = "test@email.com";
        String password = "testpassword";
        SignupRequest request = new SignupRequest(/* ... other fields */);
        OrganizationEntity expectedOrganization = new OrganizationEntity(/* ... other fields */);

    }

    @Test
    public void signup_duplicateEmail() {
        // Prepare test data
        SignupRequest request = new SignupRequest("CompanyName",
                "0555555555",
                "Baku",
                "Bob",
                "Marley",
                "existing@email.com",
                "12345");

        // Mocks
        when(userService.existsByEmail(request.getEmail())).thenReturn(true);

        // Assertion
        assertThrows(UsernameAlreadyExistsException.class, () -> {
            authService.signup(request);
        });

        // No need to verify repository interactions if we anticipate an exception
    }

    @Test
    void signin_success() {
        String email = "test@email.com";
        String password = "password";
        UserEntity user = new UserEntity();

        TokenPair tokenPair = new TokenPair("accessToken", "refreshToken");

        // Mocks
        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken(email, password));
        when(repository.findByEmail(email)).thenReturn(Optional.of(user));
        when(jwtService.generateToken(any(UserDetails.class), any(TokenType.class))).thenReturn("accessToken", "refreshToken");

        // Execute
        AuthenticationDto result = authService.signin(new SigninRequest(email, password));

        // Assertions
        // assertThat(result.getUserId()).isEqualTo(1L);
        assertThat(result.getTokenPair()).isEqualTo(tokenPair);
    }

    @Test
    void signin_invalidCredentials() {
        // Test data
        String email = "invalid@email.com";
        String password = "wrongpassword";

        // Mocks
        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(BadCredentialsException.class);

        // Execute and Assert
        assertThrows(BadCredentialsException.class, () -> authService.signin(new SigninRequest(email, password)));

        // Verify no interaction with the repository
        verify(repository, never()).findByEmail(anyString());
    }

    @Test
    void refreshToken_success() {
        // Test data
        String email = "test@email.com";
        UserEntity user = new UserEntity(/* ... user data */);
        user.setId(1L);
        TokenPair newTokenPair = new TokenPair("newAccessToken", "newRefreshToken");
        UserDetails userDetails = User.withUsername(email).password("").roles("USER").build();

        // Mocks
        when(repository.findByEmail(userDetails.getUsername())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(any(UserDetails.class), any(TokenType.class))).thenReturn("newAccessToken", "newRefreshToken");

        // Execute
        TokenPair result = authService.refreshToken(userDetails);

        // Assertions
        assertThat(result).isEqualTo(newTokenPair);
    }

    @Test
    void refreshToken_userNotFound() {
        UserDetails userDetails = User.withUsername("unknown@email.com").password("").roles("USER").build();
        when(repository.findByEmail(userDetails.getUsername())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> authService.refreshToken(userDetails));
    }

}