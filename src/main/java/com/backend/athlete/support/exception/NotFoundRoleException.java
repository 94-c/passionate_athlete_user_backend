package com.backend.athlete.support.exception;

import com.backend.athlete.support.exception.HttpException;
import org.springframework.http.HttpStatus;

public class NotFoundRoleException extends HttpException {
    private final static String MESSAGE = "해당 권한이 존재 하지 않습니다.";
    public NotFoundRoleException(HttpStatus httpStatus) {
        super(MESSAGE, httpStatus);
    }
}
