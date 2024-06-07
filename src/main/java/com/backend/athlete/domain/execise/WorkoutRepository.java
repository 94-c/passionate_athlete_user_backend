package com.backend.athlete.domain.execise;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    Optional<Workout> findByTitle(String title);
}
