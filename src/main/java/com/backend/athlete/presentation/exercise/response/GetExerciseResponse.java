package com.backend.athlete.presentation.exercise.response;

import com.backend.athlete.domain.execise.Exercise;
import lombok.Getter;

@Getter
public class GetExerciseResponse {

    private Long id;
    private String name;
    private String description;
    private String link;

    public GetExerciseResponse(Long id, String name, String description, String link) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.link = link;
    }

    public static GetExerciseResponse fromEntity(Exercise exercise) {
        return new GetExerciseResponse(
                exercise.getId(),
                exercise.getName(),
                exercise.getDescription(),
                exercise.getLink()
        );
    }

}
