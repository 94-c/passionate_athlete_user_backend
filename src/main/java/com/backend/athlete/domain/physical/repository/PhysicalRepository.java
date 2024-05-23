package com.backend.athlete.domain.physical.repository;

import com.backend.athlete.domain.physical.model.Physical;
import com.backend.athlete.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface PhysicalRepository extends JpaRepository<Physical, Long> {
    boolean existsByUserAndMeasureDate(User user, LocalDate measureDate);

}
