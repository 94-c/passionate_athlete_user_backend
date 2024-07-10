package com.backend.athlete.domain.comment.dto.response;

import com.backend.athlete.domain.comment.domain.Comment;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;
@Getter
public class GetCommentResponse {
    private Long id;
    private Long noticeId;
    private String content;
    private Long userId;
    private String userName;
    private String createdDate;
    private String modifiedDate;
    private List<GetCommentResponse> replies;

    public GetCommentResponse(Long id, Long noticeId, String content, Long userId, String userName, String createdDate, String modifiedDate, List<GetCommentResponse> replies) {
        this.id = id;
        this.noticeId = noticeId;
        this.content = content;
        this.userId = userId;
        this.userName = userName;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.replies = replies != null && !replies.isEmpty() ? replies : null;
    }

    public GetCommentResponse(Long id, Long noticeId, String content, Long userId, String userName, String createdDate, String modifiedDate) {
        this.id = id;
        this.noticeId = noticeId;
        this.content = content;
        this.userId = userId;
        this.userName = userName;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }


    public static GetCommentResponse fromEntity(Comment comment) {
        List<GetCommentResponse> replyResponses = comment.getReplies().stream()
                .map(reply -> {
                    List<GetCommentResponse> subReplies = reply.getReplies().stream()
                            .map(subReply -> new GetCommentResponse(
                                    subReply.getId(),
                                    subReply.getNotice().getId(),
                                    subReply.getContent(),
                                    subReply.getUser().getId(),
                                    subReply.getUser().getName(),
                                    subReply.getCreatedDate(),
                                    subReply.getModifiedDate()
                            ))
                            .collect(Collectors.toList());
                    return new GetCommentResponse(
                            reply.getId(),
                            reply.getNotice().getId(),
                            reply.getContent(),
                            reply.getUser().getId(),
                            reply.getUser().getName(),
                            reply.getCreatedDate(),
                            reply.getModifiedDate(),
                            subReplies
                    );
                })
                .collect(Collectors.toList());

        return new GetCommentResponse(
                comment.getId(),
                comment.getNotice().getId(),
                comment.getContent(),
                comment.getUser().getId(),
                comment.getUser().getName(),
                comment.getCreatedDate(),
                comment.getModifiedDate(),
                replyResponses
        );
    }
}
