package com.backend.athlete.domain.branch.application;

import com.backend.athlete.domain.branch.domain.BranchRepository;
import com.backend.athlete.domain.branch.dto.response.*;
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
