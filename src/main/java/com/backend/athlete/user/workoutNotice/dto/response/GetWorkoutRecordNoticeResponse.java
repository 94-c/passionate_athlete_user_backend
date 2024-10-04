package com.backend.athlete.user.workoutNotice.dto.response;

import com.backend.athlete.user.workoutNotice.domain.WorkoutRecordNotice;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetWorkoutRecordNoticeResponse {

    private Long id;
    private String branchName;
    private String userName;
    private LocalDateTime createdAt;
    private String createdDate;

    public GetWorkoutRecordNoticeResponse(Long id, String branchName, String userName, LocalDateTime createdAt, String createdDate) {
        this.id = id;
        this.branchName = branchName;
        this.userName = userName;
        this.createdAt = createdAt;
        this.createdDate = createdDate;
    }

    public static GetWorkoutRecordNoticeResponse fromEntity(WorkoutRecordNotice workoutRecordNotice) {
        return new GetWorkoutRecordNoticeResponse(
                workoutRecordNotice.getId(),
                workoutRecordNotice.getWorkoutRecord().getUser().getBranch().getName(),
                workoutRecordNotice.getWorkoutRecord().getUser().getName(),
                workoutRecordNotice.getCreatedAt(),
                workoutRecordNotice.getCreatedDate()
        );
    }
}
