package com.etaskify.etaskify.service.impl;

import com.etaskify.etaskify.dao.entity.OrganizationEntity;
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
import com.etaskify.etaskify.service.AuthenticationService;
import com.etaskify.etaskify.service.JwtService;
import com.etaskify.etaskify.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JwtService jwtService;
    private final PasswordEncoder encoder;
    private final UserService userService;
    private final UserRepository userRepository;
    private final AuthenticationManager authManager;
    private final OrganizationRepository organizationRepository;

    @Override
    @Transactional
    public long signup(SignupRequest request) {
        if (userService.existsByEmail(request.getEmail()))
            throw new UsernameAlreadyExistsException("User with email " + request.getEmail() + " already exists");

        var organization = OrganizationEntity.builder()
                .companyName(request.getCompanyName())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .build();
        organizationRepository.save(organization);

        userRepository.save(UserEntity.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .role(UserRole.ADMIN)
                .createdDate(LocalDateTime.now())
                .organization(OrganizationEntity.builder().id(organization.getId()).build())
                .build());
        return organization.getId();
    }

    @Override
    public AuthenticationDto signin(SigninRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found with email " + request.getEmail()));

        return AuthenticationDto.builder()
                .userId(user.getId())
                .tokenPair(getTokenPair(user))
                .build();
    }

    @Override
    public TokenPair refreshToken(UserDetails userDetails) {
        var user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found with email " + userDetails.getUsername()));

        return getTokenPair(user);
    }

    protected TokenPair getTokenPair(UserDetails user) {
        TokenPair tokenResponse = new TokenPair();
        tokenResponse.setAccessToken(jwtService.generateToken(user, TokenType.ACCESS));
        tokenResponse.setRefreshToken(jwtService.generateToken(user, TokenType.REFRESH));
        return tokenResponse;
    }

}
