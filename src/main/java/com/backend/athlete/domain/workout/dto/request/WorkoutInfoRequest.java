package com.backend.athlete.domain.workout.dto.request;

import com.backend.athlete.domain.workout.domain.WorkoutInfo;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkoutInfoRequest {
    private String info;

    public static WorkoutInfo toEntity(WorkoutInfoRequest request) {
        return new WorkoutInfo(request.getInfo());
    }
}
