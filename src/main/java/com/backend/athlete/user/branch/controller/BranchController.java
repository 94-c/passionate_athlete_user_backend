package com.backend.athlete.user.branch.controller;

import com.backend.athlete.user.branch.application.BranchService;
import com.backend.athlete.user.branch.dto.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/branches")
@RequiredArgsConstructor
public class BranchController {
    private final BranchService branchService;

    @GetMapping
    public ResponseEntity<List<FindAllBranchResponse>> getBranches() {
        List<FindAllBranchResponse> branches = branchService.getAllBranches();
        return ResponseEntity.status(HttpStatus.OK).body(branches);
    }

}
