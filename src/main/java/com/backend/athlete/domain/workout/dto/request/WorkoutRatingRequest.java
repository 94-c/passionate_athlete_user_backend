package com.backend.athlete.domain.workout.dto.request;

import com.backend.athlete.domain.workout.domain.WorkoutRating;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkoutRatingRequest {
    private String gender;
    private String rating;
    private String criteria;

    public static WorkoutRating toEntity(WorkoutRatingRequest request) {
        return new WorkoutRating(request.getGender(), request.getRating(), request.getCriteria());
    }
}
