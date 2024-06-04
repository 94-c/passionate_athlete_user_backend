package com.backend.athlete.presentation.user;

import com.backend.athlete.application.AuthService;
import com.backend.athlete.presentation.user.request.LoginTokenRequest;
import com.backend.athlete.presentation.user.request.RegisterUserRequest;
import com.backend.athlete.presentation.user.response.LoginTokenResponse;
import com.backend.athlete.presentation.user.response.RegisterUserResponse;
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
    public ResponseEntity<RegisterUserResponse> register(@Valid @RequestBody RegisterUserRequest request) {
        RegisterUserResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginTokenResponse> login(@RequestBody LoginTokenRequest request) {
        LoginTokenResponse response = authService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
