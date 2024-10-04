package com.backend.athlete.user.workoutNotice.dto.response;

import com.backend.athlete.user.workout.domain.WorkoutRecord;
import com.backend.athlete.user.workoutNotice.domain.WorkoutRecordNotice;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateWorkoutRecordNoticeResponse {
    private Long id;
    private WorkoutRecord workoutInfo;
    private boolean canShare;
    private LocalDateTime sharedAt;

    public CreateWorkoutRecordNoticeResponse(Long id, WorkoutRecord workoutInfo, boolean canShare, LocalDateTime sharedAt) {
        this.id = id;
        this.workoutInfo = workoutInfo;
        this.canShare = canShare;
        this.sharedAt = sharedAt;
    }

    public static CreateWorkoutRecordNoticeResponse fromEntity(WorkoutRecordNotice workoutRecordNotice, WorkoutRecord workoutRecord) {
        return new CreateWorkoutRecordNoticeResponse(
                workoutRecordNotice.getId(),
                workoutRecord,
                workoutRecordNotice.getIsShared(),
                workoutRecordNotice.getSharedAt()
        );
    }

}
