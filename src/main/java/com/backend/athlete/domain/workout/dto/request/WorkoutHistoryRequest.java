package com.backend.athlete.domain.workout.dto.request;

import com.backend.athlete.domain.execise.domain.Exercise;
import com.backend.athlete.domain.user.domain.User;
import com.backend.athlete.domain.workout.domain.WorkoutRecordHistory;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class WorkoutHistoryRequest {
    private Long userId;
    private Long exerciseId;
    private String exerciseName;
    private String weight;
    private Integer repetitions;
    private String rating;

    public WorkoutHistoryRequest(Long userId, Long exerciseId, String exerciseName, String weight, Integer repetitions, String rating) {
        this.userId = userId;
        this.exerciseId = exerciseId;
        this.exerciseName = exerciseName;
        this.weight = weight;
        this.repetitions = repetitions;
        this.rating = rating;
    }

    public static WorkoutRecordHistory toEntity(WorkoutHistoryRequest request, User user, Exercise exercise) {
        return new WorkoutRecordHistory(
                user,
                exercise,
                request.getWeight(),
                request.getRepetitions(),
                request.getRating()
        );
    }
}

