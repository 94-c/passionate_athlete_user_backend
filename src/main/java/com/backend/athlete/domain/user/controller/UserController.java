package com.backend.athlete.domain.user.controller;

import com.backend.athlete.domain.user.application.UserService;
import com.backend.athlete.domain.user.dto.request.UpdateUserRequest;
import com.backend.athlete.domain.user.dto.response.GetUserResponse;
import com.backend.athlete.domain.user.dto.response.UpdateUserResponse;
import com.backend.athlete.domain.auth.jwt.service.CustomUserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<GetUserResponse> getUserInfo(@AuthenticationPrincipal CustomUserDetailsImpl userPrincipal) {
        GetUserResponse response = userService.getUserInfo(userPrincipal);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping
    public ResponseEntity<UpdateUserResponse> updateUser(@AuthenticationPrincipal CustomUserDetailsImpl userPrincipal,
                                                         @RequestBody UpdateUserRequest request) {
        UpdateUserResponse response = userService.updateUser(userPrincipal, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }



}
