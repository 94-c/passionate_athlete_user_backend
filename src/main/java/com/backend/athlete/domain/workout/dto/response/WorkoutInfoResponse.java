package com.backend.athlete.domain.workout.dto.response;

import com.backend.athlete.domain.exercise.dto.response.GetExerciseResponse;
import com.backend.athlete.domain.workout.domain.ScheduledWorkoutInfo;
import lombok.Getter;

@Getter
public class WorkoutInfoResponse {
    private Long id;
    private GetExerciseResponse exercise;

    public WorkoutInfoResponse(Long id,GetExerciseResponse exercise) {
        this.id = id;
        this.exercise = exercise;
    }

    public static WorkoutInfoResponse fromEntity(ScheduledWorkoutInfo scheduledWorkoutInfo) {
        return new WorkoutInfoResponse(
                scheduledWorkoutInfo.getId(),
                GetExerciseResponse.fromEntity(scheduledWorkoutInfo.getExercise())
        );
    }
}
