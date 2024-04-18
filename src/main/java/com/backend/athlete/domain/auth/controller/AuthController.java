package com.backend.athlete.domain.auth.controller;

import com.backend.athlete.domain.auth.dto.LoginRequest;
import com.backend.athlete.domain.auth.dto.LoginResponse;
import com.backend.athlete.domain.auth.dto.SignUpRequest;
import com.backend.athlete.domain.auth.dto.data.LoginTokenData;
import com.backend.athlete.domain.auth.service.AuthService;
import com.backend.athlete.domain.user.domain.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

        private final AuthService authService;

        @PostMapping("/login")
        public ResponseEntity<LoginResponse> login(
                @Valid @RequestBody LoginRequest request
        ) {
                LoginTokenData jwtToken = authService.login(request);

                LoginResponse response = new LoginResponse(jwtToken);

                return new ResponseEntity<>(response, HttpStatus.OK);
        }
        @PostMapping("/sign-up")
        public ResponseEntity<User> joinUser(
                @Valid @RequestBody SignUpRequest request
        ) {
                User newUser = authService.signUp(request);

                return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        }


}
