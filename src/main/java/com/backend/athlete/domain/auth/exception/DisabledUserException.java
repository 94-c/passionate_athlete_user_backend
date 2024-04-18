package com.backend.athlete.domain.auth.exception;


import com.backend.athlete.global.payload.ApiResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.FORBIDDEN)
public class DisabledUserException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    private ApiResponse apiResponse;

    public DisabledUserException(ApiResponse apiResponse) {
        super();
        this.apiResponse = apiResponse;
    }

    public DisabledUserException(String message) {
        super(message);
    }

    public DisabledUserException(String message, Throwable cause) {
        super(message, cause);
    }

}
