package com.backend.athlete.domain.branch.infrastructure;

import com.backend.athlete.domain.branch.domain.Branch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BranchRepositoryCustom {
    Optional<Branch> findByNameContaining(String name);
    Page<Branch> findByNameContainingAndManagerNameContaining(String name, String managerName, Pageable pageable);

}
