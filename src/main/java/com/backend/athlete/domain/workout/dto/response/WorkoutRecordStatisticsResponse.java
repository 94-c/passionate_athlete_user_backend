package com.backend.athlete.domain.workout.dto.response;

import com.backend.athlete.domain.workout.domain.WorkoutRecord;
import lombok.Getter;

@Getter
public class WorkoutRecordStatisticsResponse {
    private Long id;
    private String branchName;
    private String userName;
    private String exerciseName;
    private String gender;
    private String rating;
    private String duration;
    private Boolean success;

    public WorkoutRecordStatisticsResponse(Long id, String branchName, String userName, String exerciseName, String gender, String duration, String rating, Boolean success) {
        this.id = id;
        this.branchName = branchName;
        this.userName = userName;
        this.exerciseName = exerciseName;
        this.gender = gender;
        this.duration = duration;
        this.rating = rating;
        this.success = success;
    }

    public static WorkoutRecordStatisticsResponse fromEntity(WorkoutRecord workoutRecord) {
        return new WorkoutRecordStatisticsResponse(
                workoutRecord.getId(),
                workoutRecord.getUser().getBranch().getName(),
                workoutRecord.getUser().getName(),
                workoutRecord.getExerciseName(),
                workoutRecord.getUser().getGender().toString(),
                workoutRecord.getDuration(),
                workoutRecord.getRating(),
                workoutRecord.getSuccess()
        );
    }
}
