package com.backend.athlete.user.branch.domain;

import com.backend.athlete.user.branch.infrastructure.BranchRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Long>, BranchRepositoryCustom {
    Optional<Branch> findByName(String name);
}
