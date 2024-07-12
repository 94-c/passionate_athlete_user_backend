package com.backend.athlete.domain.notice.dto.response;

import com.backend.athlete.domain.comment.domain.Comment;
import com.backend.athlete.domain.file.domain.File;
import com.backend.athlete.domain.notice.domain.Notice;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PageSearchNoticeResponse {
    private Long id;
    private String kind;
    private String title;
    private String content;
    private List<String> imagePaths; // 다중 이미지를 위한 필드
    private String userName;
    private int likeCount;
    private String createdDate;
    private String modifiedDate;
    private List<GetNoticeCommentResponse> comments;

    public PageSearchNoticeResponse(Long id, String kind, String title, String content, List<String> imagePaths, String userName, int likeCount, String createdDate, String modifiedDate, List<GetNoticeCommentResponse> comments) {
        this.id = id;
        this.kind = kind;
        this.title = title;
        this.content = content;
        this.imagePaths = imagePaths;
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

        List<String> imagePaths = notice.getFiles().stream()
                .map(File::getFilePath)
                .collect(Collectors.toList());

        return new PageSearchNoticeResponse(
                notice.getId(),
                notice.getKind().getType(),
                notice.getTitle(),
                notice.getContent(),
                imagePaths,
                notice.getUser().getName(),
                likeCount,
                notice.getCreatedDate(),
                notice.getModifiedDate(),
                commentResponses
        );
    }
}
