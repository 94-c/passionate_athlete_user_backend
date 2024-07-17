package com.backend.athlete.domain.workout.dto.request;

import com.backend.athlete.domain.execise.domain.Exercise;
import com.backend.athlete.domain.execise.domain.ExerciseType;
import com.backend.athlete.domain.workout.domain.WorkoutInfo;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkoutInfoRequest {
    private String exerciseName;
    private String info;
    private ExerciseType type;
    public static WorkoutInfo toEntity(WorkoutInfoRequest request, Exercise exercise) {
        WorkoutInfo workoutInfo = new WorkoutInfo(exercise, request.getInfo());
        workoutInfo.setExercise(exercise);
        return workoutInfo;
    }
}

