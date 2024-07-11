package com.backend.athlete.domain.execise.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateExerciseRequest {

    @NotNull(message = "운동명 입력 해주세요.")
    private String name;
    private String description;
    private String link;

}
