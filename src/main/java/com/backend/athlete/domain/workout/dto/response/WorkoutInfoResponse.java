package com.backend.athlete.domain.workout.dto.response;

import com.backend.athlete.domain.execise.dto.response.GetExerciseResponse;
import com.backend.athlete.domain.workout.domain.WorkoutInfo;
import lombok.Getter;

@Getter
public class WorkoutInfoResponse {
    private Long id;
    private String info;
    private GetExerciseResponse exercise;

    public WorkoutInfoResponse(Long id, String info, GetExerciseResponse exercise) {
        this.id = id;
        this.info = info;
        this.exercise = exercise;
    }

    public static WorkoutInfoResponse fromEntity(WorkoutInfo workoutInfo) {
        return new WorkoutInfoResponse(
                workoutInfo.getId(),
                workoutInfo.getInfo(),
                GetExerciseResponse.fromEntity(workoutInfo.getExercise())
        );
    }
}
