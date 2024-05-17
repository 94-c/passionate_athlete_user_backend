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

    /**
     * 1. 사용자 상세보기 O
     * 2. 사용자 수정하기
     * 3. 사용자 전체리스트 조회 (User 권한 이상)
     * 4. 사용자 권한 부여 (User 권한 이상)
     * 5. 사용자 삭제하기
     */

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
