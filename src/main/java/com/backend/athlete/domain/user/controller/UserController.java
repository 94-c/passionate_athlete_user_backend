package com.backend.athlete.domain.user.controller;

import com.backend.athlete.domain.user.dto.request.UpdateUserRequestDto;
import com.backend.athlete.domain.user.dto.response.GetUserResponseDto;
import com.backend.athlete.domain.user.dto.response.UpdateUserResponseDto;
import com.backend.athlete.domain.user.service.UserService;
import com.backend.athlete.global.jwt.JwtTokenProvider;
import com.backend.athlete.global.jwt.service.CustomUserDetailsImpl;
import com.sun.security.auth.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<GetUserResponseDto> getUserInfo(@AuthenticationPrincipal CustomUserDetailsImpl userPrincipal) {
        GetUserResponseDto getUserInfo = userService.getUserInfo(userPrincipal);
        return ResponseEntity.status(HttpStatus.OK).body(getUserInfo);
    }

    @PutMapping
    public ResponseEntity<UpdateUserResponseDto> updateUser(@AuthenticationPrincipal CustomUserDetailsImpl userPrincipal,
                                                            @RequestBody UpdateUserRequestDto dto) {
        UpdateUserResponseDto updateUser = userService.updateUser(userPrincipal, dto);
        return ResponseEntity.status(HttpStatus.OK).body(updateUser);
    }
}
