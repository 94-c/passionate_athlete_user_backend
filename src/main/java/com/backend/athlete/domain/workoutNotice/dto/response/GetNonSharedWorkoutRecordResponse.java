package com.backend.athlete.domain.workoutNotice.dto.response;

import com.backend.athlete.domain.workout.domain.WorkoutRecord;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetNonSharedWorkoutRecordResponse {
    private Long id;
    private String exerciseType;
    private String scheduledWorkoutTitle;
    private Integer rounds;
    private String duration;
    private String rating;
    private Boolean success;
    private LocalDateTime createdAt;
    private String createdDate;

    public GetNonSharedWorkoutRecordResponse(Long id, String exerciseType, String scheduledWorkoutTitle, Integer rounds, String duration, String rating, Boolean success, LocalDateTime createdAt, String createdDate) {
        this.id = id;
        this.exerciseType = exerciseType;
        this.scheduledWorkoutTitle = scheduledWorkoutTitle;
        this.rounds = rounds;
        this.duration = duration;
        this.rating = rating;
        this.success = success;
        this.createdAt = createdAt;
        this.createdDate = createdDate;
    }

    public static GetNonSharedWorkoutRecordResponse fromEntity(WorkoutRecord workoutRecord) {
        return new GetNonSharedWorkoutRecordResponse(
                workoutRecord.getId(),
                workoutRecord.getExerciseType().name(),
                workoutRecord.getScheduledWorkoutTitle(),
                workoutRecord.getRounds(),
                workoutRecord.getDuration(),
                workoutRecord.getRating(),
                workoutRecord.getSuccess(),
                workoutRecord.getCreatedAt(),
                workoutRecord.getCreatedDate()
        );
    }

}
