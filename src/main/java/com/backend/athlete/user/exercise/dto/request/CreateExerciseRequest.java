package com.backend.athlete.user.exercise.dto.request;

import com.backend.athlete.user.exercise.domain.Exercise;
import com.backend.athlete.user.exercise.domain.type.ExerciseType;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateExerciseRequest {
    private String name;
    private String description;
    private String link;
    private ExerciseType type;

    public CreateExerciseRequest(String name, String description, String link, ExerciseType type) {
        this.name = name;
        this.description = description;
        this.link = link;
        this.type = type;
    }

    public static Exercise toEntity(CreateExerciseRequest request) {
        return new Exercise(request.getName(), "", "", request.getType());
    }
}
