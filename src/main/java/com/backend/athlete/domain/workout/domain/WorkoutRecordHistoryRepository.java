package com.backend.athlete.domain.workout.domain;

import com.backend.athlete.domain.exercise.domain.type.ExerciseType;
import com.backend.athlete.domain.user.domain.User;
import com.backend.athlete.domain.workout.dto.response.GetExerciseTypeLastWeightResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface WorkoutRecordHistoryRepository extends JpaRepository<WorkoutRecordHistory, Long>, WorkoutRecordHistoryQueryRepository {
    List<WorkoutRecordHistory> findByWorkoutRecordId(Long workoutRecordId);
    void deleteByWorkoutRecord(WorkoutRecord workoutRecord);
}
