package com.etaskify.etaskify.service;

import com.etaskify.etaskify.model.TokenPair;
import com.etaskify.etaskify.model.dto.AuthenticationDto;
import com.etaskify.etaskify.model.request.SigninRequest;
import com.etaskify.etaskify.model.request.SignupRequest;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationService {

    long signup(SignupRequest request);

    AuthenticationDto signin(SigninRequest request);

    TokenPair refreshToken(UserDetails userDetails);

}
