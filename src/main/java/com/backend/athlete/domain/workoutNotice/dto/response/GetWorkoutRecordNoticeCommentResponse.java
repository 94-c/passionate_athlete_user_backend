package com.backend.athlete.domain.workoutNotice.dto.response;

import com.backend.athlete.domain.workoutNotice.domain.WorkoutRecordNoticeComment;
import lombok.Getter;

@Getter
public class GetWorkoutRecordNoticeCommentResponse {
    private Long id;
    private Long workoutRecordNoticeId;
    private String content;
    private Long userId;
    private String userName;
    private String createdDate;
    private String modifiedDate;

    public GetWorkoutRecordNoticeCommentResponse(Long id, Long workoutRecordNoticeId, String content, Long userId, String userName, String createdDate, String modifiedDate) {
        this.id = id;
        this.workoutRecordNoticeId = workoutRecordNoticeId;
        this.content = content;
        this.userId = userId;
        this.userName = userName;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public static GetWorkoutRecordNoticeCommentResponse fromEntity(WorkoutRecordNoticeComment workoutRecordNoticeComment) {
        return new GetWorkoutRecordNoticeCommentResponse(
                workoutRecordNoticeComment.getId(),
                workoutRecordNoticeComment.getNotice().getId(),
                workoutRecordNoticeComment.getContent(),
                workoutRecordNoticeComment.getUser().getId(),
                workoutRecordNoticeComment.getUser().getName(),
                workoutRecordNoticeComment.getCreatedDate(),
                workoutRecordNoticeComment.getModifiedDate()
        );
    }
}
