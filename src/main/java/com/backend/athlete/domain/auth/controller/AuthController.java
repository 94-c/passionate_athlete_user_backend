package com.backend.athlete.domain.auth.controller;

import com.backend.athlete.domain.auth.dto.SignUpRequest;
import com.backend.athlete.domain.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

        private final AuthService authService;

        @PostMapping("/sign-up")
        public void join(@Valid @RequestBody SignUpRequest request) {
            authService.signUp(request);
        }

}
