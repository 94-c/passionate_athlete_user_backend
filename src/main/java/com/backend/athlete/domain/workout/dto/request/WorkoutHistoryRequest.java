package com.backend.athlete.domain.workout.dto.request;

import com.backend.athlete.domain.exercise.domain.Exercise;
import com.backend.athlete.domain.user.domain.User;
import com.backend.athlete.domain.workout.domain.WorkoutRecordHistory;
import com.backend.athlete.domain.workout.domain.type.WeightUnit;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class WorkoutHistoryRequest {
    private Long userId;
    private Long exerciseId;
    private String exerciseName;
    private String weightValue;    // 무게 값
    private String weightUnit;     // 무게 단위 (WeightUnit의 문자열 값)
    private Integer rounds;
    private String rating;

    public WorkoutHistoryRequest(Long userId, Long exerciseId, String exerciseName, String weightValue, String weightUnit, Integer rounds, String rating) {
        this.userId = userId;
        this.exerciseId = exerciseId;
        this.exerciseName = exerciseName;
        this.weightValue = weightValue;
        this.weightUnit = weightUnit;
        this.rounds = rounds;
        this.rating = rating;
    }

    public static WorkoutRecordHistory toEntity(WorkoutHistoryRequest request, User user, Exercise exercise) {
        WeightUnit unit = WeightUnit.valueOf(request.getWeightUnit());
        return new WorkoutRecordHistory(
                user,
                exercise,
                unit,
                request.getWeightValue(),
                request.getRounds(),
                request.getRating()
        );
    }
}

