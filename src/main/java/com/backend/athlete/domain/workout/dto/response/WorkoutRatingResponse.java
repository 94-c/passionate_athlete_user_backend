package com.backend.athlete.domain.workout.dto.response;


import com.backend.athlete.domain.workout.domain.WorkoutRating;
import lombok.Getter;

@Getter
public class WorkoutRatingResponse {
    private Long id;
    private String gender;
    private String rating;
    private String criteria;

    public WorkoutRatingResponse(Long id, String gender, String rating, String criteria) {
        this.id = id;
        this.gender = gender;
        this.rating = rating;
        this.criteria = criteria;
    }

    public static WorkoutRatingResponse fromEntity(WorkoutRating workoutRating) {
        return new WorkoutRatingResponse(workoutRating.getId(), workoutRating.getGender(), workoutRating.getRating(), workoutRating.getCriteria());
    }
}
