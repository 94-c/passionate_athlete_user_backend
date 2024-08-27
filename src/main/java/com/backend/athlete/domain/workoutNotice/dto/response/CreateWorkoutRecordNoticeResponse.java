package com.backend.athlete.domain.workoutNotice.dto.response;

import com.backend.athlete.domain.workout.domain.WorkoutRecord;
import com.backend.athlete.domain.workout.dto.response.GetWorkoutRecordAndHistoryResponse;
import com.backend.athlete.domain.workout.dto.response.WorkoutRecordHistoryResponse;
import com.backend.athlete.domain.workoutNotice.domain.WorkoutRecordNotice;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CreateWorkoutRecordNoticeResponse {
    private Long id;
    private GetWorkoutRecordAndHistoryResponse workoutInfo;
    private boolean canShare;
    private LocalDateTime sharedAt;

    public CreateWorkoutRecordNoticeResponse(Long id, GetWorkoutRecordAndHistoryResponse workoutInfo, boolean canShare, LocalDateTime sharedAt) {
        this.id = id;
        this.workoutInfo = workoutInfo;
        this.canShare = canShare;
        this.sharedAt = sharedAt;
    }

    public static CreateWorkoutRecordNoticeResponse fromEntity(WorkoutRecordNotice workoutRecordNotice, WorkoutRecord workoutRecord, List<WorkoutRecordHistoryResponse> historie) {
        return new CreateWorkoutRecordNoticeResponse(
                workoutRecordNotice.getId(),
                GetWorkoutRecordAndHistoryResponse.fromEntity(workoutRecord, historie),
                workoutRecordNotice.getIsShared(),
                workoutRecordNotice.getSharedAt()
        );
    }

}
