package com.backend.athlete.domain.exercise.dto.request;

import com.backend.athlete.domain.exercise.domain.Exercise;
import com.backend.athlete.domain.exercise.domain.type.ExerciseType;
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
