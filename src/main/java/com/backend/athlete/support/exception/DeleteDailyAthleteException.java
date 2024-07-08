package com.backend.athlete.support.exception;

import org.springframework.http.HttpStatus;

public class DeleteDailyAthleteException extends HttpException{
    private final static String MESSAGE = "운동 기록이 발견되지 않았거나 승인되지 않았습니다.";
    public DeleteDailyAthleteException(HttpStatus status) {
        super(MESSAGE, status);
    }
}
