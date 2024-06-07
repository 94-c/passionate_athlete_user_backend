package com.backend.athlete.presentation.exercise.request;

import com.backend.athlete.domain.execise.Exercise;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateExerciseRequest {

    @NotNull(message = "운동명 입력 해주세요.")
    private String name;
    private String description;
    private String link;
    public static Exercise toEntity(CreateExerciseRequest request) {
        return new Exercise(
                request.getName(),
                request.getDescription(),
                request.getLink()
        );
    }
}
