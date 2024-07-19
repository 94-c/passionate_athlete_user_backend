package com.backend.athlete.domain.workout.dto.response;

import com.backend.athlete.domain.workout.domain.WorkoutRecord;
import com.backend.athlete.domain.workout.domain.type.WorkoutRecordType;
import lombok.Getter;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CreateWorkoutRecordResponse {
    private Long id;
    private String userName;
    private WorkoutRecordType exerciseType;
    private String scheduledWorkoutTitle;
    private String exerciseName;
    private Integer rounds;
    private String duration;
    private String rating;
    private Boolean success;
    private String recordContent;
    private String createdDate;
    private List<WorkoutRecordHistoryResponse> workoutHistories;

    public CreateWorkoutRecordResponse(Long id, String userName, WorkoutRecordType exerciseType,
                                       String scheduledWorkoutTitle, String exerciseName, Integer rounds,
                                       String duration, String rating, Boolean success, String recordContent,
                                       String createdDate, List<WorkoutRecordHistoryResponse> workoutHistories) {
        this.id = id;
        this.userName = userName;
        this.exerciseType = exerciseType;
        this.scheduledWorkoutTitle = scheduledWorkoutTitle;
        this.exerciseName = exerciseName;
        this.rounds = rounds;
        this.duration = duration;
        this.rating = rating;
        this.success = success;
        this.recordContent = recordContent;
        this.createdDate = createdDate;
        this.workoutHistories = workoutHistories;
    }

    public static CreateWorkoutRecordResponse fromEntity(WorkoutRecord workoutRecord) {
        String scheduledWorkoutTitle = workoutRecord.getScheduledWorkout() != null
                ? workoutRecord.getScheduledWorkout().getTitle()
                : null;

        List<WorkoutRecordHistoryResponse> workoutHistories = workoutRecord.getWorkoutHistories() != null
                ? workoutRecord.getWorkoutHistories().stream()
                .map(WorkoutRecordHistoryResponse::fromEntity)
                .collect(Collectors.toList())
                : new ArrayList<>();

        return new CreateWorkoutRecordResponse(
                workoutRecord.getId(),
                workoutRecord.getUser().getName(),
                workoutRecord.getExerciseType(),
                scheduledWorkoutTitle,
                workoutRecord.getScheduledWorkout().toString(),
                workoutRecord.getRounds(),
                workoutRecord.getDuration(),
                workoutRecord.getRating(),
                workoutRecord.getSuccess(),
                workoutRecord.getRecordContent(),
                workoutRecord.getCreatedDate().toString(),
                workoutHistories
        );
    }
}

