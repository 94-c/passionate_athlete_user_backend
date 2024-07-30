package com.backend.athlete.domain.workout.dto.response;


import com.backend.athlete.domain.workout.domain.ScheduledWorkoutRating;
import lombok.Getter;

@Getter
public class WorkoutRatingResponse {
    private String rating;
    private String criteria;

    public WorkoutRatingResponse(String rating, String criteria) {
        this.rating = rating;
        this.criteria = criteria;
    }

    public static WorkoutRatingResponse fromEntity(ScheduledWorkoutRating scheduledWorkoutRating) {
        return new WorkoutRatingResponse(scheduledWorkoutRating.getRating(), scheduledWorkoutRating.getCriteria());
    }
}
