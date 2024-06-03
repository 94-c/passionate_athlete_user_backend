package com.backend.athlete.domain.user.controller;

import com.backend.athlete.domain.user.dto.request.UpdateUserRequest;
import com.backend.athlete.domain.user.dto.request.UpdateUserRoleRequest;
import com.backend.athlete.domain.user.dto.request.UpdateUserStatusRequest;
import com.backend.athlete.domain.user.dto.response.GetUserResponse;
import com.backend.athlete.domain.user.dto.response.UpdateUserResponse;
import com.backend.athlete.domain.user.dto.response.UpdateUserRoleResponse;
import com.backend.athlete.domain.user.dto.response.UpdateUserStatusResponse;
import com.backend.athlete.domain.user.service.UserService;
import com.backend.athlete.global.jwt.service.CustomUserDetailsImpl;
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
    public ResponseEntity<GetUserResponse> getUserInfo(@AuthenticationPrincipal CustomUserDetailsImpl userPrincipal) {
        GetUserResponse getUserInfo = userService.getUserInfo(userPrincipal);
        return ResponseEntity.status(HttpStatus.OK).body(getUserInfo);
    }

    @PutMapping
    public ResponseEntity<UpdateUserResponse> updateUser(@AuthenticationPrincipal CustomUserDetailsImpl userPrincipal,
                                                         @RequestBody UpdateUserRequest dto) {
        UpdateUserResponse updateUser = userService.updateUser(userPrincipal, dto);
        return ResponseEntity.status(HttpStatus.OK).body(updateUser);
    }

    /**
     * 회원 상태 변경
     * 회원 권한 변경
     */
    @PutMapping("/{userId}/status")
    @PreAuthorize("hasAnyAuthority('MANAGER') or hasAnyAuthority('ADMIN')")
    public ResponseEntity<UpdateUserStatusResponse> updateStatus(@PathVariable Long userId,
                                                                 @RequestBody UpdateUserStatusRequest request) {
        UpdateUserStatusResponse response = userService.updateUserStatus(userId, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{userId}/role")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<UpdateUserRoleResponse> updateRole(@PathVariable Long userId,
                                                             @RequestBody UpdateUserRoleRequest request) {
        UpdateUserRoleResponse response = userService.updateUserRole(userId, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
