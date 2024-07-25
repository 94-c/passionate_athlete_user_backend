package com.backend.athlete.domain.workout.domain.data;

import com.backend.athlete.domain.workout.domain.WorkoutRecord;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class WorkoutRecordData {
    private Long id;
    private String exerciseType;
    private Integer rounds;
    private String duration;
    private String rating;
    private Boolean success;
    private String recordContent;
    private LocalDateTime createdAt;

    public WorkoutRecordData(WorkoutRecord workoutRecord) {
        this.id = workoutRecord.getId();
        this.exerciseType = workoutRecord.getExerciseType().name();
        this.rounds = workoutRecord.getRounds();
        this.duration = workoutRecord.getDuration();
        this.rating = workoutRecord.getRating();
        this.success = workoutRecord.getSuccess();
        this.recordContent = workoutRecord.getRecordContent();
        this.createdAt = workoutRecord.getCreatedAt();
    }
}
