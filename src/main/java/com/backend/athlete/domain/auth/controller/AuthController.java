package com.backend.athlete.domain.auth.controller;

import com.backend.athlete.domain.auth.application.AuthService;
import com.backend.athlete.domain.auth.dto.request.*;
import com.backend.athlete.domain.auth.dto.response.FindUserIdResponse;
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
    public ResponseEntity<Boolean> checkUserId(@RequestParam(name = "userId") String userId) {
        boolean response = authService.checkUserIdExists(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/check-user-phone")
    public ResponseEntity<Boolean> checkUserPhone(@RequestParam(name = "phoneNumber") String phoneNumber) {
        boolean response = authService.checkUserPhoneExists(phoneNumber);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify-user")
    public ResponseEntity<Boolean> verifyUser(@RequestBody VerifyUserRequest request) {
        boolean response = authService.verifyUser(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Boolean> resetPassword(@RequestBody ResetPasswordRequest request) {
        boolean response = authService.resetPassword(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/find-user-id")
    public ResponseEntity<FindUserIdResponse> findUserId(@RequestBody FindUserIdRequest request) {
        FindUserIdResponse response = authService.findUserId(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
