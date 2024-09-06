package com.backend.athlete.domain.workoutNotice.dto.request;

import com.backend.athlete.domain.user.domain.User;
import com.backend.athlete.domain.workoutNotice.domain.WorkoutRecordNotice;
import com.backend.athlete.domain.workoutNotice.domain.WorkoutRecordNoticeComment;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateWorkoutRecordNoticeCommentRequest {
    @NotNull(message = "댓글 내용을 입력 해주세요.")
    private String content;

    public static WorkoutRecordNoticeComment toEntity(CreateWorkoutRecordNoticeCommentRequest request, WorkoutRecordNotice workoutRecordNotice, User user) {
        return new WorkoutRecordNoticeComment(user, workoutRecordNotice, request.getContent());
    }
}
