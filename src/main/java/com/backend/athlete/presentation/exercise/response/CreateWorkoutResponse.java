package com.backend.athlete.presentation.exercise.response;

import com.backend.athlete.domain.execise.Workout;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CreateWorkoutResponse {

    private Long id;
    private String title;
    private String description;
    private List<GetWorkoutInfoResponse> workoutInfos;

    public CreateWorkoutResponse(Long id, String title, String description, List<GetWorkoutInfoResponse> workoutInfos) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.workoutInfos = workoutInfos;
    }

    public static CreateWorkoutResponse fromEntity(Workout workout) {
        List<GetWorkoutInfoResponse> workoutInfoResponses = workout.getWorkoutInfos().stream()
                .map(GetWorkoutInfoResponse::fromEntity)
                .collect(Collectors.toList());

        return new CreateWorkoutResponse(
                workout.getId(),
                workout.getTitle(),
                workout.getDescription(),
                workoutInfoResponses
        );
    }
}
