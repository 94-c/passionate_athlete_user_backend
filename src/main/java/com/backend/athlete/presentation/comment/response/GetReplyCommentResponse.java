package com.backend.athlete.presentation.comment.response;

import com.backend.athlete.domain.comment.Comment;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class GetReplyCommentResponse {
    private Long id;
    private String content;
    private Long userId;
    private String userName;
    private String createdDate;
    private String modifiedDate;
    private List<GetReplyCommentResponse> replies;

    public GetReplyCommentResponse(Long id, String content, Long userId, String userName, String createdDate, String modifiedDate, List<GetReplyCommentResponse> replies) {
        this.id = id;
        this.content = content;
        this.userId = userId;
        this.userName = userName;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.replies = replies != null && !replies.isEmpty() ? replies : null;
    }

    public static GetReplyCommentResponse fromEntity(Comment comment) {
        List<GetReplyCommentResponse> replyResponses = comment.getReplies().stream()
                .map(GetReplyCommentResponse::fromEntity)
                .collect(Collectors.toList());

        return new GetReplyCommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getUser().getId(),
                comment.getUser().getName(),
                comment.getCreatedDate().toString(),
                comment.getModifiedDate().toString(),
                replyResponses
        );
    }
}

