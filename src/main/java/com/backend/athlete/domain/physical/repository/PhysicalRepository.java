package com.backend.athlete.domain.physical.repository;

import com.backend.athlete.domain.physical.model.Physical;
import com.backend.athlete.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PhysicalRepository extends JpaRepository<Physical, Long> {
    boolean existsByUserAndMeasureDate(User user, LocalDate measureDate);

    @Query("SELECT a FROM Physical a WHERE a.user.id = :userId AND a.measureDate = :measureDate" )
    List<Physical> findPhysicalsByUserIdAAndAndMeasureDate(@Param("userId") Long id, @Param("measureDate") LocalDate measureDate);
}
