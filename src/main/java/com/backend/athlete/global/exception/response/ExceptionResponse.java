package com.backend.athlete.global.exception.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
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
