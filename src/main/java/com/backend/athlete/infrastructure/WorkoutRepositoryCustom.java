package com.backend.athlete.infrastructure;

import com.backend.athlete.domain.execise.Workout;

public interface WorkoutRepositoryCustom {
    Workout findWorkoutWithDetailsById(Long id);
}
