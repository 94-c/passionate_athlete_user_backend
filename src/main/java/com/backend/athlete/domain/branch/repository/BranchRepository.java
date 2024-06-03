package com.backend.athlete.domain.branch.repository;

import com.backend.athlete.domain.branch.model.Branch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Long> {
    @Query("SELECT b FROM Branch b LEFT JOIN FETCH b.users WHERE b.name = :name")
    Optional<Branch> findByName(@Param("name") String name);
    @Query("SELECT b FROM Branch b WHERE b.name LIKE %:name%")
    Optional<Branch> findByNameContaining(String name);
    @Query("SELECT b FROM Branch b LEFT JOIN b.manager m WHERE b.name LIKE %:name% AND (m.name LIKE %:managerName% OR :managerName IS NULL)")
    Page<Branch> findByNameContainingAndManagerNameContaining(@Param("name") String name, @Param("managerName") String managerName, Pageable pageable);

}
