package com.backend.athlete.infrastructure;

import com.backend.athlete.domain.branch.Branch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BranchRepositoryCustom {
    Optional<Branch> findByName(String name);
    Optional<Branch> findByNameContaining(String name);
    Page<Branch> findByNameContainingAndManagerNameContaining(String name, String managerName, Pageable pageable);

}
