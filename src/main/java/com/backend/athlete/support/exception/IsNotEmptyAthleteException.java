package com.backend.athlete.support.exception;

import org.springframework.http.HttpStatus;

public class IsNotEmptyAthleteException extends HttpException {
    private final static String MESSAGE = "날짜에 해당하는 운동을 찾을 수 없습니다.";
    public IsNotEmptyAthleteException(HttpStatus httpStatus) {
        super(MESSAGE, httpStatus);
    }
}
