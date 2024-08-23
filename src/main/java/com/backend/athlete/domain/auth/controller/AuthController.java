package com.backend.athlete.domain.auth.controller;

import com.backend.athlete.domain.auth.application.AuthService;
import com.backend.athlete.domain.auth.dto.request.CheckUserIdRequest;
import com.backend.athlete.domain.auth.dto.request.LoginRequest;
import com.backend.athlete.domain.auth.dto.request.RegisterRequest;
import com.backend.athlete.domain.auth.dto.response.LoginResponse;
import com.backend.athlete.domain.auth.dto.response.RegisterResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    /**
     * 1. 회원 회원가입
     * 2. 회원 로그인
     * 3. 아이디 중복 체크
     */
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        RegisterResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/check-userid")
    public ResponseEntity<Boolean> checkUserId(@RequestParam(name = "userId") String userId) {boolean exists = authService.checkUserIdExists(userId);
        return ResponseEntity.ok(exists);
    }

}
