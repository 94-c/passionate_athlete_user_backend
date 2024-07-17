package com.backend.athlete.domain.workout.dto.response;

import com.backend.athlete.domain.workout.domain.WorkoutRecordHistory;
import lombok.Getter;

@Getter
public class WorkoutRecordHistoryResponse {
    private String exerciseName;
    private String weight;
    private Integer repetitions;
    private String rating;

    public WorkoutRecordHistoryResponse(String exerciseName, String weight, Integer repetitions, String rating) {
        this.exerciseName = exerciseName;
        this.weight = weight;
        this.repetitions = repetitions;
        this.rating = rating;
    }

    public static WorkoutRecordHistoryResponse fromEntity(WorkoutRecordHistory workoutRecordHistory) {
        return new WorkoutRecordHistoryResponse(
                workoutRecordHistory.getExercise().getName(),
                workoutRecordHistory.getWeight(),
                workoutRecordHistory.getRepetitions(),
                workoutRecordHistory.getRating()
        );
    }
}
