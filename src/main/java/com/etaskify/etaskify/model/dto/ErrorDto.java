package com.etaskify.etaskify.model.dto;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class ErrorDto<T extends Throwable> {

    int errorCode;
    String errorMessage;
    Class<T> exceptionType;
    LocalDateTime localDateTime;

}
