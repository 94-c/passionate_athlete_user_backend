package com.backend.athlete.domain.workout.domain.data;

import com.backend.athlete.domain.workout.domain.WorkoutRecord;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class WorkoutRecordCalendarData {
    private Long id;
    private String exerciseType;
    private LocalDateTime createdAt;
    private String scheduledWorkoutTitle;

    public WorkoutRecordCalendarData(WorkoutRecord workoutRecord) {
        this.id = workoutRecord.getId();
        this.exerciseType = workoutRecord.getExerciseType().name();
        this.createdAt = workoutRecord.getCreatedAt();
        this.scheduledWorkoutTitle = workoutRecord.getScheduledWorkoutTitle() != null ? workoutRecord.getScheduledWorkoutTitle() : null;
    }
}
