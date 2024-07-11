package com.backend.athlete.domain.execise.dto.response;

import com.backend.athlete.domain.execise.domain.Workout;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class GetWorkoutResponse {

    private Long id;
    private String title;
    private String description;
    private List<GetWorkoutInfoResponse> workoutInfos;

    public GetWorkoutResponse(Long id, String title, String description, List<GetWorkoutInfoResponse> workoutInfos) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.workoutInfos = workoutInfos;
    }

    public static GetWorkoutResponse fromEntity(Workout workout) {
        List<GetWorkoutInfoResponse> workoutInfoResponses = workout.getWorkoutInfos().stream()
                .map(GetWorkoutInfoResponse::fromEntity)
                .collect(Collectors.toList());

        return new GetWorkoutResponse(
                workout.getId(),
                workout.getTitle(),
                workout.getDescription(),
                workoutInfoResponses
        );
    }

}
