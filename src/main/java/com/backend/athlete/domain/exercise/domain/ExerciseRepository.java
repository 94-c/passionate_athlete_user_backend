package com.backend.athlete.domain.exercise.domain;

import com.backend.athlete.domain.exercise.domain.type.ExerciseType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    Optional<Exercise> findByName(String name);

    List<Exercise> findByType(ExerciseType type);
}
