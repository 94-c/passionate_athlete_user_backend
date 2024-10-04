package com.backend.athlete.user.exercise.dto.response;

import com.backend.athlete.user.exercise.domain.Exercise;
import lombok.Getter;

@Getter
public class CreateExerciseResponse {
    private Long id;
    private String name;
    private String description;
    private String link;
    private String type;

    public CreateExerciseResponse(Long id, String name, String description, String link, String type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.link = link;
        this.type = type;
    }

    public static CreateExerciseResponse fromEntity(Exercise exercise) {
        return new CreateExerciseResponse(
                exercise.getId(),
                exercise.getName(),
                exercise.getDescription(),
                exercise.getLink(),
                exercise.getType().name()
        );
    }
}
