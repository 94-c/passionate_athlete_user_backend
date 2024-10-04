package com.backend.athlete.user.comment.data;

import lombok.Getter;

@Getter
public class ReplyCommentData {
    private Long id;
    private String content;
    private Long userId;
    private String userName;
    private String createdDate;
    private String modifiedDate;
    public ReplyCommentData(Long id, String content, Long userId, String userName, String createdDate, String modifiedDate) {
        this.id = id;
        this.content = content;
        this.userId = userId;
        this.userName = userName;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
