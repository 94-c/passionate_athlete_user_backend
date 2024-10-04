package com.backend.athlete.user.workout.dto.response;

import com.backend.athlete.user.exercise.domain.type.ExerciseType;
import lombok.Getter;

@Getter
public class GetExerciseTypeLastWeightResponse {
    private String exerciseType;
    private String exerciseName;
    private String weight;

    public GetExerciseTypeLastWeightResponse(ExerciseType exerciseType, String exerciseName, String weight) {
        this.exerciseType = exerciseType.name();
        this.exerciseName = exerciseName;
        this.weight = weight;
    }
}
