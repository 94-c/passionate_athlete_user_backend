package com.backend.athlete.domain.workout.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface WorkoutRecordHistoryRepository extends JpaRepository<WorkoutRecordHistory, Long> {
    List<WorkoutRecordHistory> findByWorkoutRecordId(Long workoutRecordId);
}
