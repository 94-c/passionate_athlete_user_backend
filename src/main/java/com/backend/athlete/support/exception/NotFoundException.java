package com.backend.athlete.support.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends HttpException{
    public NotFoundException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
