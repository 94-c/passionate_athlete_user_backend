package com.backend.athlete.domain.workout.dto.response;

import com.backend.athlete.domain.exercise.dto.response.GetExerciseResponse;
import com.backend.athlete.domain.workout.domain.ScheduledWorkoutInfo;
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

    public static WorkoutInfoResponse fromEntity(ScheduledWorkoutInfo scheduledWorkoutInfo) {
        return new WorkoutInfoResponse(
                scheduledWorkoutInfo.getId(),
                scheduledWorkoutInfo.getInfo(),
                GetExerciseResponse.fromEntity(scheduledWorkoutInfo.getExercise())
        );
    }
}
