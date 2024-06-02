package com.backend.athlete.domain.comment.dto.response;

import com.backend.athlete.domain.comment.model.Comment;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetCommentResponse {
    private Long id;
    private String content;
    private String userName;
    private String createdDate;
    private String modifiedDate;
    private List<GetCommentResponse> replies = new ArrayList<>();

    public GetCommentResponse(Long id, String content, String userName, String createdDate, String modifiedDate, List<GetCommentResponse> replies) {
        this.id = id;
        this.content = content;
        this.userName = userName;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.replies = replies != null && !replies.isEmpty() ? replies : null;
    }

    public GetCommentResponse(Long id, String content, String userName, String createdDate, String modifiedDate) {
        this.id = id;
        this.content = content;
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
                                    subReply.getContent(),
                                    subReply.getUser().getName(),
                                    subReply.getCreatedDate(),
                                    subReply.getModifiedDate(),
                                    null
                            ))
                            .collect(Collectors.toList());
                    return new GetCommentResponse(
                            reply.getId(),
                            reply.getContent(),
                            reply.getUser().getName(),
                            reply.getCreatedDate(),
                            reply.getModifiedDate(),
                            subReplies
                    );
                })
                .collect(Collectors.toList());

        return new GetCommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getUser().getName(),
                comment.getCreatedDate(),
                comment.getModifiedDate(),
                replyResponses
        );
    }
}