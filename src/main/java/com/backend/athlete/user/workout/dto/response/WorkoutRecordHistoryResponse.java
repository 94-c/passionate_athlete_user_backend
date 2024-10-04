package com.backend.athlete.user.workout.dto.response;

import com.backend.athlete.user.workout.domain.WorkoutRecordHistory;
import com.backend.athlete.user.workout.domain.type.WeightUnit;
import lombok.Getter;

@Getter
public class WorkoutRecordHistoryResponse {
    private String exerciseName;
    private Double weightValue;  // 무게 값
    private WeightUnit unit;     // 무게 단위
    private Integer repetitions;
    private String rating;

    public WorkoutRecordHistoryResponse(String exerciseName, Double weightValue, WeightUnit unit, Integer repetitions, String rating) {
        this.exerciseName = exerciseName;
        this.weightValue = weightValue;
        this.unit = unit;
        this.repetitions = repetitions;
        this.rating = rating;
    }

    public static WorkoutRecordHistoryResponse fromEntity(WorkoutRecordHistory workoutRecordHistory) {
        return new WorkoutRecordHistoryResponse(
                workoutRecordHistory.getExercise().getName(),
                Double.valueOf(workoutRecordHistory.getWeight()), // 무게 값은 Double로 변환
                workoutRecordHistory.getUnit(),
                workoutRecordHistory.getRounds(),
                workoutRecordHistory.getRating()
        );
    }
}
