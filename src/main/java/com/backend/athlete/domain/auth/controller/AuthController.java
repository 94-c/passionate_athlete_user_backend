package com.backend.athlete.domain.auth.controller;

import com.backend.athlete.domain.auth.dto.request.RegisterUserRequestDto;
import com.backend.athlete.domain.auth.dto.response.RegisterUserResponseDto;
import com.backend.athlete.domain.auth.service.AuthService;
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
    public ResponseEntity<RegisterUserResponseDto> register(@RequestBody RegisterUserRequestDto dto) {
        authService.register(dto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
