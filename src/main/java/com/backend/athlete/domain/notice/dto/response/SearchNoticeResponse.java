package com.backend.athlete.domain.notice.dto.response;

import com.backend.athlete.domain.comment.model.Comment;
import com.backend.athlete.domain.notice.model.Notice;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
public class SearchNoticeResponse {
    private Long id;
    private String title;
    private String content;
    private String imagePath;
    private String userName;
    private int likeCount;
    private List<GetNoticeCommentResponse> comments;

    public SearchNoticeResponse(Long id, String title, String content, String imagePath, String userName, int likeCount, List<GetNoticeCommentResponse> comments) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.imagePath = imagePath;
        this.userName = userName;
        this.likeCount = likeCount;
        this.comments = comments;
    }

    public static SearchNoticeResponse fromEntity(Notice notice, int likeCount, List<Comment> comments) {
        List<GetNoticeCommentResponse> commentResponses = comments.stream()
                .map(comment -> new GetNoticeCommentResponse(
                        comment.getId(),
                        comment.getContent(),
                        comment.getUser().getName(),
                        comment.getCreatedDate()))
                .collect(Collectors.toList());

        return new SearchNoticeResponse(
                notice.getId(),
                notice.getTitle(),
                notice.getContent(),
                notice.getImagePath(),
                notice.getUser().getName(),
                likeCount,
                commentResponses
        );
    }


}
