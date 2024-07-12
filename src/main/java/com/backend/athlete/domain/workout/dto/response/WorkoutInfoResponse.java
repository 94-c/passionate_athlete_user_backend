package com.backend.athlete.domain.workout.dto.response;

import com.backend.athlete.domain.workout.domain.WorkoutInfo;
import lombok.Getter;

@Getter
public class WorkoutInfoResponse {
    private Long id;
    private String info;

    public WorkoutInfoResponse(Long id, String info) {
        this.id = id;
        this.info = info;
    }

    public static WorkoutInfoResponse fromEntity(WorkoutInfo workoutInfo) {
        return new WorkoutInfoResponse(workoutInfo.getId(), workoutInfo.getInfo());
    }
}
