package com.backend.athlete.support.exception;

import com.backend.athlete.support.exception.HttpException;
import org.springframework.http.HttpStatus;

public class NotFoundBranchException extends HttpException {
    private final static String MESSAGE = "해당 지점을 찾을 수 없습니다.";
    public NotFoundBranchException(HttpStatus httpStatus) {
        super(MESSAGE, httpStatus);
    }
}
