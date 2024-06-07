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

    public CreateWorkoutResponse(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public static CreateWorkoutResponse toEntity(Workout workout) {
        return new CreateWorkoutResponse(
                workout.getId(),
                workout.getTitle(),
                workout.getDescription()
        );
    }

}
