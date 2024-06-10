package com.backend.athlete.infrastructure;

import com.backend.athlete.domain.execise.Workout;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WorkoutRepositoryCustom {
    Workout findWorkoutWithDetailsById(Long id);
    Page<Workout> findAllWithDetails(String title, Pageable pageable);

}
