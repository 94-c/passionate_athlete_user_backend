package com.backend.athlete.presentation;

import com.backend.athlete.application.AuthService;
import com.backend.athlete.presentation.request.LoginTokenRequest;
import com.backend.athlete.presentation.request.RegisterUserRequest;
import com.backend.athlete.presentation.response.LoginTokenResponse;
import com.backend.athlete.presentation.response.RegisterUserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> register(@Valid @RequestBody RegisterUserRequest dto) {
        RegisterUserResponse register = authService.register(dto);
        return ResponseEntity.status(HttpStatus.OK).body(register);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginTokenResponse> login(@RequestBody LoginTokenRequest dto) {
        LoginTokenResponse login = authService.login(dto);
        return ResponseEntity.status(HttpStatus.OK).body(login);
    }

}
