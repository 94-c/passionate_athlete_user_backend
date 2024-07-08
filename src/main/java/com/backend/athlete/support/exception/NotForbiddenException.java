package com.backend.athlete.support.exception;

import org.springframework.http.HttpStatus;

public class NotForbiddenException extends HttpException{
    public NotForbiddenException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
