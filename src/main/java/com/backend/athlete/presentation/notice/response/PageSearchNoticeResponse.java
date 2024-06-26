package com.backend.athlete.presentation.notice.response;

import com.backend.athlete.domain.comment.Comment;
import com.backend.athlete.domain.notice.Notice;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PageSearchNoticeResponse {
    private Long id;
    private Integer kind;
    private String title;
    private String content;
    private String imagePath;
    private String userName;
    private int likeCount;
    private String createdDate;
    private String modifiedDate;
    private List<GetNoticeCommentResponse> comments;

    public PageSearchNoticeResponse(Long id, Integer kind, String title, String content, String imagePath, String userName, int likeCount, String createdDate, String modifiedDate, List<GetNoticeCommentResponse> comments) {
        this.id = id;
        this.kind = kind;
        this.title = title;
        this.content = content;
        this.imagePath = imagePath;
        this.userName = userName;
        this.likeCount = likeCount;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.comments = comments;
    }

    public static PageSearchNoticeResponse fromEntity(Notice notice, int likeCount, List<Comment> comments) {
        List<GetNoticeCommentResponse> commentResponses = comments.stream()
                .map(comment -> new GetNoticeCommentResponse(
                        comment.getId(),
                        comment.getContent(),
                        comment.getUser().getId(),
                        comment.getUser().getName(),
                        comment.getCreatedDate()))
                .collect(Collectors.toList());

        return new PageSearchNoticeResponse(
                notice.getId(),
                notice.getKind(),
                notice.getTitle(),
                notice.getContent(),
                notice.getImagePath(),
                notice.getUser().getName(),
                likeCount,
                notice.getCreatedDate(),
                notice.getModifiedDate(),
                commentResponses
        );
    }


}
