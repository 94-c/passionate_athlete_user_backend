package com.backend.athlete.global.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class AuthException extends RuntimeException{

    private HttpStatus status;
    private String message;

    public AuthException(String message, HttpStatus status) {
        super(message);
        this.message = message;
        this.status = status;
    }

}
