package com.backend.athlete.domain.auth.exception;

import com.backend.athlete.support.exception.HttpException;
import org.springframework.http.HttpStatus;

public class IsExistUserIddException extends HttpException {
    private final static String MESSAGE = "이미 사용중인 아이디입니다.";
    public IsExistUserIddException(HttpStatus httpStatus) {
        super(MESSAGE, httpStatus);
    }
}
