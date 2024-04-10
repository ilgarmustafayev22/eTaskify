package com.etaskify.etaskify.controller;

import com.etaskify.etaskify.exception.UserNotFoundException;
import com.etaskify.etaskify.model.dto.UserDto;
import com.etaskify.etaskify.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void createUser_validUserDto_returnsCreatedStatusAndUserId() {
        UserDto userDto = new UserDto(123L, "name", "surname", "test@email.com", "password");
        long expectedUserId = 123L;
        when(userService.createUser(userDto)).thenReturn(expectedUserId);

        ResponseEntity<Long> result = userController.createUser(userDto);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isEqualTo(expectedUserId);
        verify(userService).createUser(userDto);
    }


    @Test
    void getUser_existingUser_returnsOkAndUserDto() {
        long userId = 1L;
        UserDto expectedUserDto = new UserDto(123L, "name", "surname", "test@email.com", "password");
        when(userService.findById(userId)).thenReturn(expectedUserDto);

        ResponseEntity<UserDto> result = userController.getUser(userId);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(expectedUserDto);
    }

    @Test
    void getUser_nonexistentUser_returnsNotFound() {
        long nonExistentUserId = 999L;

    }

    @Test
    void deleteUser_existingUser_returnsNoContent() {
        long userId = 1L;
        doNothing().when(userService).deleteUser(userId);

        ResponseEntity<UserDto> response = userController.deleteUser(userId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(userService).deleteUser(userId);
    }

    @Test
    void deleteUser_nonexistentUser_returnsNotFound() {
        long nonExistentUserId = 999L;

    }

}
