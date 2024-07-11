package com.backend.athlete.domain.execise.dto.request;

import com.backend.athlete.domain.execise.domain.Exercise;
import com.backend.athlete.domain.execise.domain.Workout;
import com.backend.athlete.domain.execise.domain.WorkoutInfo;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CreateWorkoutInfoRequest {

    @NotNull(message = "상세 운동을 입력해주세요.")
    private Long exerciseId;
    private List<CreateWorkoutLevelRequest> levels;

    public CreateWorkoutInfoRequest(Long exerciseId, List<CreateWorkoutLevelRequest> levels) {
        this.exerciseId = exerciseId;
        this.levels = levels;
    }

    public static WorkoutInfo toEntity(CreateWorkoutInfoRequest request, Workout workout, Exercise exercise) {
        return new WorkoutInfo(
                workout,
                exercise,
                request.getLevels()
        );
    }

}
