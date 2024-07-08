package com.backend.athlete.support.exception;

import com.backend.athlete.support.exception.HttpException;
import org.springframework.http.HttpStatus;

public class DuplicatePasswordException extends HttpException {
    private final static String MESSAGE = "비밀번호가 일치 하지 않습니다.";
    public DuplicatePasswordException(HttpStatus httpStatus) {
        super(MESSAGE, httpStatus);
    }
}
