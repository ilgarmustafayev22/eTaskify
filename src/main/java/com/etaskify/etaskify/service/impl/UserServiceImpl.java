package com.etaskify.etaskify.service.impl;

import com.etaskify.etaskify.dao.entity.OrganizationEntity;
import com.etaskify.etaskify.dao.entity.UserEntity;
import com.etaskify.etaskify.dao.repositroy.UserRepository;
import com.etaskify.etaskify.exception.UserNotFoundException;
import com.etaskify.etaskify.mapper.UserMapper;
import com.etaskify.etaskify.model.dto.UserDto;
import com.etaskify.etaskify.model.enums.UserRole;
import com.etaskify.etaskify.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public long createUser(UserDto user) {
        UserEntity userEntity = UserEntity.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .password(encoder.encode(user.getPassword()))
                .role(UserRole.USER)
                .createdDate(LocalDateTime.now())
                .organization(OrganizationEntity.builder().id(user.getOrganizationId()).build())
                .build();
        userRepository.save(userEntity);
        return userEntity.getId();
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserDto findById(long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + id));
    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

}
