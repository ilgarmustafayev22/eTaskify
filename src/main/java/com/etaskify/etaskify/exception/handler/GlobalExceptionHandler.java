package com.etaskify.etaskify.exception.handler;

import com.etaskify.etaskify.exception.OrganizationNotFoundException;
import com.etaskify.etaskify.exception.TaskNotFoundException;
import com.etaskify.etaskify.exception.UserNotFoundException;
import com.etaskify.etaskify.exception.UsernameAlreadyExistsException;
import com.etaskify.etaskify.model.dto.ErrorDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = UsernameAlreadyExistsException.class)
    public ResponseEntity<ErrorDto<UsernameAlreadyExistsException>> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex) {
        log.error(ex.getMessage());

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new ErrorDto<>(409,
                        ex.getMessage(),
                        UsernameAlreadyExistsException.class,
                        LocalDateTime.now()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<ErrorDto<UserNotFoundException>> handleUserNotFoundException(UserNotFoundException ex) {
        log.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorDto<>(404,
                        ex.getMessage(),
                        UserNotFoundException.class,
                        LocalDateTime.now()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = TaskNotFoundException.class)
    public ResponseEntity<ErrorDto<TaskNotFoundException>> handleTaskNotFoundException(TaskNotFoundException ex) {
        log.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorDto<>(404,
                        ex.getMessage(),
                        TaskNotFoundException.class,
                        LocalDateTime.now()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = OrganizationNotFoundException.class)
    public ResponseEntity<ErrorDto<OrganizationNotFoundException>> handleOrganizationNotFoundException(OrganizationNotFoundException ex) {
        log.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorDto<>(404,
                        ex.getMessage(),
                        OrganizationNotFoundException.class,
                        LocalDateTime.now()));
    }

}
