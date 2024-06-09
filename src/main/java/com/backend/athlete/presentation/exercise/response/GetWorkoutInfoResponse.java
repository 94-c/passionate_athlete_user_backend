package com.backend.athlete.presentation.exercise.response;

import com.backend.athlete.domain.execise.WorkoutInfo;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class GetWorkoutInfoResponse {

    private Long id;
    private GetExerciseResponse exercise;
    private List<GetWorkoutLevelResponse> levels;

    public GetWorkoutInfoResponse(Long id, GetExerciseResponse exercise, List<GetWorkoutLevelResponse> levels) {
        this.id = id;
        this.exercise = exercise;
        this.levels = levels;
    }

    public static GetWorkoutInfoResponse fromEntity(WorkoutInfo workoutInfo) {
        GetExerciseResponse exerciseResponse = GetExerciseResponse.fromEntity(workoutInfo.getExercise());
        List<GetWorkoutLevelResponse> levelResponses = workoutInfo.getLevels().stream()
                .map(GetWorkoutLevelResponse::fromEntity)
                .collect(Collectors.toList());

        return new GetWorkoutInfoResponse(
                workoutInfo.getId(),
                exerciseResponse,
                levelResponses
        );
    }

}
