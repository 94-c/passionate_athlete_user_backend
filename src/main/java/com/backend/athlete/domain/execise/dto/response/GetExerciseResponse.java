package com.backend.athlete.domain.execise.dto.response;

import com.backend.athlete.domain.execise.domain.Exercise;
import lombok.Getter;

@Getter
public class GetExerciseResponse {
    private Long id;
    private String name;
    private String description;
    private String link;
    private String type;

    public GetExerciseResponse(Long id, String name, String description, String link, String type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.link = link;
        this.type = type;
    }

    public static GetExerciseResponse fromEntity(Exercise exercise) {
        return new GetExerciseResponse(
                exercise.getId(),
                exercise.getName(),
                exercise.getDescription(),
                exercise.getLink(),
                exercise.getType().name()
        );
    }
}
