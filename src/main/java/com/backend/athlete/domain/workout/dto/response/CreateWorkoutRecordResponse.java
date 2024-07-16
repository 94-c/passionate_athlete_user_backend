package com.backend.athlete.domain.workout.dto.response;

import com.backend.athlete.domain.workout.domain.WorkoutRecord;
import com.backend.athlete.domain.workout.domain.type.WorkoutRecordType;
import lombok.Getter;

@Getter
public class CreateWorkoutRecordResponse {
    private Long id;
    private String userName;
    private WorkoutRecordType exerciseType;
    private String scheduledWorkoutTitle;
    private String exerciseName;
    private Integer repetitions;
    private String duration;
    private String rating;
    private Boolean success;
    private String recordContent;
    private String createdDate;

    public CreateWorkoutRecordResponse(Long id, String userName, WorkoutRecordType exerciseType,
                                       String scheduledWorkoutTitle, String exerciseName, Integer repetitions,
                                       String duration, String rating, Boolean success, String recordContent, String createdDate) {
        this.id = id;
        this.userName = userName;
        this.exerciseType = exerciseType;
        this.scheduledWorkoutTitle = scheduledWorkoutTitle;
        this.exerciseName = exerciseName;
        this.repetitions = repetitions;
        this.duration = duration;
        this.rating = rating;
        this.success = success;
        this.recordContent = recordContent;
        this.createdDate = createdDate;
    }

    public static CreateWorkoutRecordResponse fromEntity(WorkoutRecord workoutRecord) {
        String scheduledWorkoutTitle = workoutRecord.getScheduledWorkout() != null
                ? workoutRecord.getScheduledWorkout().getTitle()
                : null;

        return new CreateWorkoutRecordResponse(
                workoutRecord.getId(),
                workoutRecord.getUser().getName(),
                workoutRecord.getExerciseType(),
                scheduledWorkoutTitle,
                workoutRecord.getExerciseName(),
                workoutRecord.getRepetitions(),
                workoutRecord.getDuration(),
                workoutRecord.getRating(),
                workoutRecord.getSuccess(),
                workoutRecord.getRecordContent(),
                workoutRecord.getCreatedDate()
        );
    }
}
