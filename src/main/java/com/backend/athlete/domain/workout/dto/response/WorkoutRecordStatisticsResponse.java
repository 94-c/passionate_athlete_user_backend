package com.backend.athlete.domain.workout.dto.response;

import com.backend.athlete.domain.workout.domain.WorkoutRecord;
import lombok.Getter;

@Getter
public class WorkoutRecordStatisticsResponse {
    private Long id;
    private String userName;
    private String exerciseName;
    private String duration;
    private String rating;
    private Boolean success;

    public WorkoutRecordStatisticsResponse(Long id, String userName, String exerciseName, String duration, String rating, Boolean success) {
        this.id = id;
        this.userName = userName;
        this.exerciseName = exerciseName;
        this.duration = duration;
        this.rating = rating;
        this.success = success;
    }

    public static WorkoutRecordStatisticsResponse fromEntity(WorkoutRecord workoutRecord) {
        return new WorkoutRecordStatisticsResponse(
                workoutRecord.getId(),
                workoutRecord.getUser().getName(),
                workoutRecord.getExerciseName(),
                workoutRecord.getDuration(),
                workoutRecord.getRating(),
                workoutRecord.getSuccess()
        );
    }
}
