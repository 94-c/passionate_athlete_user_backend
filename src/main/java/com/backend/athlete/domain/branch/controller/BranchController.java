package com.backend.athlete.domain.branch.controller;

import com.backend.athlete.domain.branch.dto.request.CreateBranchRequest;
import com.backend.athlete.domain.branch.dto.request.PageSearchBranchRequest;
import com.backend.athlete.domain.branch.dto.request.UpdateBranchRequest;
import com.backend.athlete.domain.branch.dto.response.*;
import com.backend.athlete.domain.branch.service.BranchService;
import com.backend.athlete.global.constant.PageConstant;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/branches")
public class BranchController {

    private final BranchService branchService;

    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('MANAGER') or hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> searchNotice(
            @RequestParam(defaultValue = "", required = false) String name,
            @RequestParam(defaultValue = "", required = false) String managerName,
            @RequestParam(defaultValue = PageConstant.DEFAULT_PAGE, required = false) int page,
            @RequestParam(defaultValue = PageConstant.DEFAULT_PER_PAGE, required = false) int perPage
    ) {
        PageSearchBranchRequest request = new PageSearchBranchRequest(name, managerName);
        Page<PageSearchBranchResponse> response = branchService.findAllBranches(request, page, perPage);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<CreateBranchResponse> createBranch(@Valid @RequestBody CreateBranchRequest request) {
        CreateBranchResponse response = branchService.createBranch(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('MANAGER') or hasAnyAuthority('ADMIN')")
    public ResponseEntity<GetBranchResponse> getBranch(@PathVariable Long id) {
        GetBranchResponse response = branchService.getBranch(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<UpdateBranchResponse> updateBranch(@PathVariable Long id,
                                                             @Valid @RequestBody UpdateBranchRequest request) {
        UpdateBranchResponse response = branchService.updateBranch(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('MANAGER') or hasAnyAuthority('ADMIN')")
    public ResponseEntity<GetBranchUsersResponse> searchBranchUsers(@RequestParam String name) {
        GetBranchUsersResponse response = branchService.searchBranchUsersByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}