package com.backend.athlete.domain.execise.dto.request;

import com.backend.athlete.domain.execise.domain.Exercise;
import com.backend.athlete.domain.execise.domain.ExerciseType;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateExerciseRequest {
    private String name;
    private ExerciseType type;

    public CreateExerciseRequest(String name, ExerciseType type) {
        this.name = name;
        this.type = type;
    }

    public static Exercise toEntity(CreateExerciseRequest request) {
        return new Exercise(request.getName(), "", "", request.getType());
    }
}
