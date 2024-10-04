package com.backend.athlete.user.workout.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutRecordHistoryRepository extends JpaRepository<WorkoutRecordHistory, Long>, WorkoutRecordHistoryQueryRepository {
    List<WorkoutRecordHistory> findByWorkoutRecordId(Long workoutRecordId);
    void deleteByWorkoutRecord(WorkoutRecord workoutRecord);
}
