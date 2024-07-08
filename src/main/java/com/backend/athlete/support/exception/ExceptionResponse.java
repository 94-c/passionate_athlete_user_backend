package com.backend.athlete.support.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ExceptionResponse {
    private int status;
    private String message;
    private long timestamp;

    @Builder
    public ExceptionResponse(int status, String message, long timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }

}
