package com.backend.athlete.domain.admin.controller;

import com.backend.athlete.domain.admin.application.AdminService;
import com.backend.athlete.domain.admin.dto.branch.*;
import com.backend.athlete.domain.athlete.dto.request.CreateAthleteRequest;
import com.backend.athlete.domain.athlete.dto.response.CreateAthleteResponse;
import com.backend.athlete.domain.auth.jwt.service.CustomUserDetailsImpl;
import com.backend.athlete.domain.admin.dto.user.UpdateUserRoleRequest;
import com.backend.athlete.domain.admin.dto.user.UpdateUserStatusRequest;
import com.backend.athlete.domain.admin.dto.user.UpdateUserRoleResponse;
import com.backend.athlete.domain.admin.dto.user.UpdateUserStatusResponse;
import com.backend.athlete.support.constant.PageConstant;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @PostMapping("/athletes")
    @PreAuthorize("hasAnyAuthority('USER') or hasAnyAuthority('MANAGER')")
    public ResponseEntity<CreateAthleteResponse> createAthlete(@AuthenticationPrincipal CustomUserDetailsImpl userPrincipal,
                                                               @Valid @RequestBody CreateAthleteRequest request) {
        CreateAthleteResponse response = adminService.createAthlete(userPrincipal, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/admin/branches")
    @PreAuthorize("hasAnyAuthority('MANAGER') or hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> searchNotice(
            @RequestParam(defaultValue = "", required = false) String name,
            @RequestParam(defaultValue = "", required = false) String managerName,
            @RequestParam(defaultValue = PageConstant.DEFAULT_PAGE, required = false) int page,
            @RequestParam(defaultValue = PageConstant.DEFAULT_PER_PAGE, required = false) int perPage
    ) {
        PageSearchBranchRequest request = new PageSearchBranchRequest(name, managerName);
        Page<PageSearchBranchResponse> response = adminService.findAllBranches(request, page, perPage);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @PostMapping("/admin/branches")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<CreateBranchResponse> createBranch(@Valid @RequestBody CreateBranchRequest request) {
        CreateBranchResponse response = adminService.createBranch(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/admin/branches/{id}")
    @PreAuthorize("hasAnyAuthority('MANAGER') or hasAnyAuthority('ADMIN')")
    public ResponseEntity<GetBranchResponse> getBranch(@PathVariable Long id) {
        GetBranchResponse response = adminService.getBranch(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/admin/branches/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<UpdateBranchResponse> updateBranch(@PathVariable Long id,
                                                             @Valid @RequestBody UpdateBranchRequest request) {
        UpdateBranchResponse response = adminService.updateBranch(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/admin/branches/search")
    @PreAuthorize("hasAnyAuthority('MANAGER') or hasAnyAuthority('ADMIN')")
    public ResponseEntity<GetBranchUsersResponse> searchBranchUsers(@RequestParam String name) {
        GetBranchUsersResponse response = adminService.searchBranchUsersByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
