package com.etaskify.etaskify.service.impl;

import com.etaskify.etaskify.dao.entity.OrganizationEntity;
import com.etaskify.etaskify.dao.entity.TaskEntity;
import com.etaskify.etaskify.dao.entity.UserEntity;
import com.etaskify.etaskify.dao.repositroy.UserRepository;
import com.etaskify.etaskify.exception.UserNotFoundException;
import com.etaskify.etaskify.mapper.UserMapper;
import com.etaskify.etaskify.model.dto.UserDto;
import com.etaskify.etaskify.model.enums.UserRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void existsByEmail() {
        String email = "test@example.com";

        when(userRepository.existsByEmail(email)).thenReturn(true); // Or .thenReturn(false)

        boolean exists = userService.existsByEmail(email);

        assertTrue(exists); // Or assertFalse in the other case
    }

    @Test
    void findById_success() {
        // Prepare test data
        long userId = 1L;
        UserEntity userEntity = new UserEntity(/*... set fields here... */);
        userEntity.setId(userId);
        UserDto expectedUserDto = new UserDto(/*... map from userEntity ... */);

        // Mocks
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(userMapper.toDto(userEntity)).thenReturn(expectedUserDto);

        // Execute the method
        UserDto actualUserDto = userService.findById(userId);

        // Assertions
        assertThat(actualUserDto).isEqualTo(expectedUserDto);
    }

    @Test
    void findById_notFound() {
        // Prepare test data
        long userId = 1L;

        // Mocks
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Assertions
        assertThatThrownBy(() -> userService.findById(userId))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("User not found with id " + userId);
    }

    @Test
    void deleteUser_success() {
        // Prepare data
        long userId = 1L;

        // Mocks
        doNothing().when(userRepository).deleteById(userId); // Mockito for void methods

        // Execute the method
        userService.deleteUser(userId);

        // Assertions
        verify(userRepository, times(1)).deleteById(userId);
    }

}
