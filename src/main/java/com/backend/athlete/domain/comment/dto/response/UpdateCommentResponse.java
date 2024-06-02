package com.backend.athlete.domain.comment.dto.response;

import com.backend.athlete.domain.comment.model.Comment;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
public class UpdateCommentResponse {
    private Long id;
    private String content;
    private String userName;
    private String modifiedDate;
    private List<UpdateCommentResponse> replies;

    public UpdateCommentResponse(Long id, String content, String userName, String modifiedDate, List<UpdateCommentResponse> replies) {
        this.id = id;
        this.content = content;
        this.userName = userName;
        this.modifiedDate = modifiedDate;
        this.replies = replies;
    }

    public static UpdateCommentResponse fromEntity(Comment comment) {
        List<UpdateCommentResponse> replyResponses = comment.getReplies().stream()
                .map(UpdateCommentResponse::fromEntity)
                .collect(Collectors.toList());

        return new UpdateCommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getUser().getName(),
                comment.getModifiedDate(),
                replyResponses
        );
    }
}
