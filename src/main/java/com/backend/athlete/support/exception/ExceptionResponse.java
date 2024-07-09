package com.backend.athlete.support.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ExceptionResponse {
    private final int status;
    private final String message;
    private final long timestamp;
    @Builder
    public ExceptionResponse(int status, String message, long timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }

}
