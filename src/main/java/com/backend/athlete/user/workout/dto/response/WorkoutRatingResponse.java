package com.backend.athlete.user.workout.dto.response;


import com.backend.athlete.user.workout.domain.ScheduledWorkoutRating;
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
