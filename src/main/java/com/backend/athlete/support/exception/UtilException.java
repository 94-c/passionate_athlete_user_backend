package com.backend.athlete.support.exception;

public class UtilException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public UtilException(String message) {
        super(message);
    }
    public UtilException(String message, Throwable cause) {
        super(message, cause);
    }
}
