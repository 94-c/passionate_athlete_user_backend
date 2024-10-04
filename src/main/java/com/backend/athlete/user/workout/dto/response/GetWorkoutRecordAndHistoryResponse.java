package com.backend.athlete.user.workout.dto.response;

import com.backend.athlete.user.workout.domain.WorkoutRecord;
import lombok.Getter;

import java.util.List;

@Getter
public class GetWorkoutRecordAndHistoryResponse {
    private Long id;
    private String scheduledWorkoutTitle;
    private String exerciseType;
    private Integer rounds;
    private String duration;
    private String rating;
    private Boolean success;
    private String recordContent;
    private List<WorkoutRecordHistoryResponse> histories;

    public GetWorkoutRecordAndHistoryResponse(Long id, String scheduledWorkoutTitle, String exerciseType, Integer rounds, String duration, String rating, Boolean success, String recordContent, List<WorkoutRecordHistoryResponse> histories) {
        this.id = id;
        this.scheduledWorkoutTitle = scheduledWorkoutTitle;
        this.exerciseType = exerciseType;
        this.rounds = rounds;
        this.duration = duration;
        this.rating = rating;
        this.success = success;
        this.recordContent = recordContent;
        this.histories = histories;
    }

    public static GetWorkoutRecordAndHistoryResponse fromEntity(WorkoutRecord workoutRecord, List<WorkoutRecordHistoryResponse> histories) {
        return new GetWorkoutRecordAndHistoryResponse(
                workoutRecord.getId(),
                workoutRecord.getScheduledWorkout() != null ? workoutRecord.getScheduledWorkout().getTitle() : null,
                workoutRecord.getExerciseType().name(),
                workoutRecord.getRounds(),
                workoutRecord.getDuration(),
                workoutRecord.getRating(),
                workoutRecord.getSuccess(),
                workoutRecord.getRecordContent(),
                histories
        );
    }

}
