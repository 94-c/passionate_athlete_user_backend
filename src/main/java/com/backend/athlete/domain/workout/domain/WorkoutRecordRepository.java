package com.backend.athlete.domain.workout.domain;

import com.backend.athlete.domain.workout.infrastructure.WorkoutRecordRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutRecordRepository extends JpaRepository<WorkoutRecord, Long>, WorkoutRecordRepositoryCustom {
}
