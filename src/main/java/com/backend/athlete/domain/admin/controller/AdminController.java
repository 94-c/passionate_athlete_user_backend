package com.backend.athlete.domain.admin.controller;

import com.backend.athlete.domain.admin.application.AdminService;
import com.backend.athlete.domain.user.application.UserService;
import com.backend.athlete.domain.admin.dto.UpdateUserRoleRequest;
import com.backend.athlete.domain.admin.dto.UpdateUserStatusRequest;
import com.backend.athlete.domain.admin.dto.UpdateUserRoleResponse;
import com.backend.athlete.domain.admin.dto.UpdateUserStatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AdminController {


    /**
     * TODO : 어드민 프로젝트로 이관 예정
     */

    private final AdminService adminService;

    @PutMapping("/users/{userId}/status")
    @PreAuthorize("hasAnyAuthority('MANAGER') or hasAnyAuthority('ADMIN')")
    public ResponseEntity<UpdateUserStatusResponse> updateStatus(@PathVariable Long userId,
                                                                 @RequestBody UpdateUserStatusRequest request) {
        UpdateUserStatusResponse response = adminService.updateUserStatus(userId, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/users/{userId}/role")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<UpdateUserRoleResponse> updateRole(@PathVariable Long userId,
                                                             @RequestBody UpdateUserRoleRequest request) {
        UpdateUserRoleResponse response = adminService.updateUserRole(userId, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
