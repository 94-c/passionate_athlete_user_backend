package com.backend.athlete.domain.workoutNotice.dto.response;

import com.backend.athlete.domain.workoutNotice.domain.WorkoutRecordNoticeComment;
import lombok.Getter;

@Getter
public class UpdateWorkoutRecordNoticeCommentResponse {
    private Long id;
    private String content;
    private String userName;
    private String modifiedDate;

    public UpdateWorkoutRecordNoticeCommentResponse(Long id, String content, String userName, String modifiedDate) {
        this.id = id;
        this.content = content;
        this.userName = userName;
        this.modifiedDate = modifiedDate;
    }

    public static UpdateWorkoutRecordNoticeCommentResponse fromEntity(WorkoutRecordNoticeComment workoutRecordNoticeComment) {
        return new UpdateWorkoutRecordNoticeCommentResponse(
                workoutRecordNoticeComment.getId(),
                workoutRecordNoticeComment.getContent(),
                workoutRecordNoticeComment.getUser().getName(),
                workoutRecordNoticeComment.getModifiedDate()
        );
    }

}
