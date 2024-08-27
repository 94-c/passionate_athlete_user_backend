package com.backend.athlete.domain.workout.domain;

import com.backend.athlete.domain.exercise.domain.type.ExerciseType;
import com.backend.athlete.domain.user.domain.User;
import com.backend.athlete.domain.workout.dto.response.GetExerciseTypeLastWeightResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface WorkoutRecordHistoryRepository extends JpaRepository<WorkoutRecordHistory, Long> {
    List<WorkoutRecordHistory> findByWorkoutRecordId(Long workoutRecordId);
    @Query("SELECT new com.backend.athlete.domain.workout.dto.response.GetExerciseTypeLastWeightResponse(e.type, e.name, w.weight) " +
            "FROM WorkoutRecordHistory w " +
            "JOIN w.exercise e " +
            "WHERE w.user.id = :userId " +
            "AND w.id IN (SELECT MAX(w2.id) FROM WorkoutRecordHistory w2 WHERE w2.user.id = :userId GROUP BY w2.exercise.id) " +
            "AND e.type = :exerciseType")
    List<GetExerciseTypeLastWeightResponse> findLastWeightsByExerciseType(@Param("userId") Long userId, @Param("exerciseType") ExerciseType exerciseType);

    List<WorkoutRecordHistory> findByWorkoutRecord(WorkoutRecord workoutRecord);

}
