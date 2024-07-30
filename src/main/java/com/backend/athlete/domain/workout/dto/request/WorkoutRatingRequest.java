package com.backend.athlete.domain.workout.dto.request;

import com.backend.athlete.domain.workout.domain.ScheduledWorkoutRating;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkoutRatingRequest {
    private String gender;
    private String rating;
    private String criteria;

    public static ScheduledWorkoutRating toEntity(WorkoutRatingRequest request) {
        return new ScheduledWorkoutRating(request.getGender(), request.getRating(), request.getCriteria());
    }
}
