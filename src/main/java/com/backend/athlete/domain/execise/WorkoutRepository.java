package com.backend.athlete.domain.execise;

import com.backend.athlete.infrastructure.WorkoutRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkoutRepository extends JpaRepository<Workout, Long>, WorkoutRepositoryCustom {
    Optional<Workout> findByTitle(String title);
}
