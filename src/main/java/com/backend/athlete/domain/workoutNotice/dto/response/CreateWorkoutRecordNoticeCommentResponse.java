package com.backend.athlete.domain.workoutNotice.dto.response;

import com.backend.athlete.domain.workoutNotice.domain.WorkoutRecordNotice;
import com.backend.athlete.domain.workoutNotice.domain.WorkoutRecordNoticeComment;
import lombok.Getter;

@Getter
public class CreateWorkoutRecordNoticeCommentResponse {
    private Long id;
    private String userName;
    private String content;
    private String createdDate;

    public CreateWorkoutRecordNoticeCommentResponse(Long id, String userName, String content, String createdDate) {
        this.id = id;
        this.userName = userName;
        this.content = content;
        this.createdDate = createdDate;
    }

    public static CreateWorkoutRecordNoticeCommentResponse fromEntity(WorkoutRecordNoticeComment workoutRecordNoticeComment) {
        return new CreateWorkoutRecordNoticeCommentResponse(
                workoutRecordNoticeComment.getId(),
                workoutRecordNoticeComment.getUser().getName(),
                workoutRecordNoticeComment.getContent(),
                workoutRecordNoticeComment.getCreatedDate()
        );
    }

}
