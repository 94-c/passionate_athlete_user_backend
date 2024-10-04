package com.backend.athlete.user.branch.application;

import com.backend.athlete.user.branch.domain.BranchRepository;
import com.backend.athlete.user.branch.dto.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BranchService {
    private final BranchRepository branchRepository;

    public List<FindAllBranchResponse> getAllBranches() {
        return branchRepository.findAll().stream()
                .map(FindAllBranchResponse::fromEntity)
                .collect(Collectors.toList());
    }

}
