package com.backend.athlete.support.exception;

public class CustomJwtException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CustomJwtException(String message) {
        super(message);
    }

    public CustomJwtException(String message, Throwable cause) {
        super(message, cause);
    }

}
