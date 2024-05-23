package com.backend.athlete.domain.physical.repository;

import com.backend.athlete.domain.physical.model.Physical;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhysicalRepository extends JpaRepository<Physical, Long> {

}
