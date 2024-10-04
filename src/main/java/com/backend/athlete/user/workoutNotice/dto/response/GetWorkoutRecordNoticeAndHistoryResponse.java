package com.backend.athlete.user.workoutNotice.dto.response;

import com.backend.athlete.user.workout.dto.response.WorkoutRecordHistoryResponse;
import com.backend.athlete.user.workoutNotice.domain.WorkoutRecordNotice;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class GetWorkoutRecordNoticeAndHistoryResponse {
    private Long id;
    private String branchName;
    private String userName;
    private LocalDateTime createdAt;
    private String createdDate;
    private String scheduledWorkoutTitle;
    private String exerciseType;
    private Integer rounds;
    private String duration;
    private String rating;
    private Boolean success;
    private String recordContent;
    private List<WorkoutRecordHistoryResponse> histories;

    public GetWorkoutRecordNoticeAndHistoryResponse(Long id, String branchName, String userName, LocalDateTime createdAt, String createdDate, String scheduledWorkoutTitle, String exerciseType, Integer rounds, String duration, String rating, Boolean success, String recordContent, List<WorkoutRecordHistoryResponse> histories) {
        this.id = id;
        this.branchName = branchName;
        this.userName = userName;
        this.createdAt = createdAt;
        this.createdDate = createdDate;
        this.scheduledWorkoutTitle = scheduledWorkoutTitle;
        this.exerciseType = exerciseType;
        this.rounds = rounds;
        this.duration = duration;
        this.rating = rating;
        this.success = success;
        this.recordContent = recordContent;
        this.histories = histories;
    }

    public static GetWorkoutRecordNoticeAndHistoryResponse fromEntity (WorkoutRecordNotice workoutRecordNotice, List<WorkoutRecordHistoryResponse> histories) {
        return new GetWorkoutRecordNoticeAndHistoryResponse(
               workoutRecordNotice.getId(),
                workoutRecordNotice.getWorkoutRecord().getUser().getBranch().getName(),
                workoutRecordNotice.getWorkoutRecord().getUser().getName(),
                workoutRecordNotice.getCreatedAt(),
                workoutRecordNotice.getCreatedDate(),
                workoutRecordNotice.getWorkoutRecord().getScheduledWorkout() != null ? workoutRecordNotice.getWorkoutRecord().getScheduledWorkout().getTitle() : null,
                workoutRecordNotice.getWorkoutRecord().getExerciseType().name(),
                workoutRecordNotice.getWorkoutRecord().getRounds(),
                workoutRecordNotice.getWorkoutRecord().getDuration(),
                workoutRecordNotice.getWorkoutRecord().getRating(),
                workoutRecordNotice.getWorkoutRecord().getSuccess(),
                workoutRecordNotice.getWorkoutRecord().getRecordContent(),
                histories
        );
    }
}
