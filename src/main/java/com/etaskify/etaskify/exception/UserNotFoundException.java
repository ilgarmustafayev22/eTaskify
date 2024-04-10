package com.etaskify.etaskify.exception;

import jakarta.validation.constraints.NotBlank;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }

}
