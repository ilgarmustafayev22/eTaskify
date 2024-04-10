package com.etaskify.etaskify.controller;

import com.etaskify.etaskify.model.TokenPair;
import com.etaskify.etaskify.model.dto.AuthenticationDto;
import com.etaskify.etaskify.model.request.SigninRequest;
import com.etaskify.etaskify.model.request.SignupRequest;
import com.etaskify.etaskify.service.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationController authenticationController;

    @Test
    void signup_validRequest_returnsUserId() {
        SignupRequest signupRequest = new SignupRequest("companyname",
                "111111",
                "Baku",
                "Bob",
                "testuser",
                "test@email.com",
                "password");
        Long expectedUserId = 123L;
        when(authenticationService.signup(signupRequest)).thenReturn(expectedUserId);

        ResponseEntity<Long> result = authenticationController.signup(signupRequest);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(expectedUserId);
        verify(authenticationService).signup(signupRequest);
    }

    @Test
    void signin_validCredentials_returnsAuthenticationDto() {
        SigninRequest signinRequest = new SigninRequest("testuser@gmail.com", "password");
        TokenPair tokenPair = new TokenPair("accessToken", "refreshToken");
        AuthenticationDto expectedAuthDto = new AuthenticationDto(1L,tokenPair); // Adapt to your DTO structure
        when(authenticationService.signin(signinRequest)).thenReturn(expectedAuthDto);

        ResponseEntity<AuthenticationDto> result = authenticationController.signin(signinRequest);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(expectedAuthDto);
        verify(authenticationService).signin(signinRequest);
    }

    @Test
    void refreshToken_validUserDetails_returnsNewTokenPair() {
        UserDetails userDetails = mock(UserDetails.class);

        TokenPair expectedTokenPair = new TokenPair("newAccessToken", "newRefreshToken");
        when(authenticationService.refreshToken(userDetails)).thenReturn(expectedTokenPair);

        ResponseEntity<TokenPair> result = authenticationController.refreshToken(userDetails);


        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(expectedTokenPair);
        verify(authenticationService).refreshToken(userDetails);
    }

}