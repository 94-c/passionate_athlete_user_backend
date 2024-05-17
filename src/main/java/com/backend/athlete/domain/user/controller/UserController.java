package com.backend.athlete.domain.user.controller;

import com.backend.athlete.domain.user.dto.response.GetUserResponseDto;
import com.backend.athlete.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 1. 사용자 상세보기
     * 2. 사용자 수정하기
     * 3. 사용자 전체리스트 조회 (User 권한 이상)
     * 4. 사용자 권한 부여 (User 권한 이상)
     * 5. 사용자 삭제하기
     */

    @GetMapping("")
    public ResponseEntity<GetUserResponseDto> getUserInfo(Authentication authentication) {
        String userId = authentication.getName();
        GetUserResponseDto findByUserInfo = userService.getUserInfo(userId);
        return ResponseEntity.status(HttpStatus.OK).body(findByUserInfo);
    }
}
