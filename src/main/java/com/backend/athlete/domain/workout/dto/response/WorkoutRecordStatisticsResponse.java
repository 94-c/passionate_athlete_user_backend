package com.backend.athlete.domain.workout.dto.response;

import com.backend.athlete.domain.workout.domain.WorkoutRecord;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class WorkoutRecordStatisticsResponse {
    private Long id;
    private String branchName;
    private String userName;
    private String gender;
    private String duration;
    private String rating;
    private Boolean success;
    private List<WorkoutRecordHistoryResponse> workoutHistories;

    public WorkoutRecordStatisticsResponse(Long id, String branchName, String userName, String gender, String duration, String rating, Boolean success, List<WorkoutRecordHistoryResponse> workoutHistories) {
        this.id = id;
        this.branchName = branchName;
        this.userName = userName;
        this.gender = gender;
        this.duration = duration;
        this.rating = rating;
        this.success = success;
        this.workoutHistories = workoutHistories;
    }

    public static WorkoutRecordStatisticsResponse fromEntity(WorkoutRecord workoutRecord) {
        List<WorkoutRecordHistoryResponse> workoutHistories = workoutRecord.getWorkoutHistories().stream()
                .map(WorkoutRecordHistoryResponse::fromEntity)
                .collect(Collectors.toList());

        return new WorkoutRecordStatisticsResponse(
                workoutRecord.getId(),
                workoutRecord.getUser().getBranch().getName(),
                workoutRecord.getUser().getName(),
                workoutRecord.getUser().getGender().toString(),
                workoutRecord.getDuration(),
                workoutRecord.getRating(),
                workoutRecord.getSuccess(),
                workoutHistories
        );
    }
}

