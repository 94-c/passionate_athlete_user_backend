package com.backend.athlete.presentation.notice.response;

import com.backend.athlete.domain.notice.Notice;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class GetNoticeResponse {
    private Long id;
    private String title;
    private String content;
    private String imagePath;
    private String userName;
    private int likeCount;
    private String createdDate;
    private List<GetNoticeCommentResponse> comments;

    public GetNoticeResponse(Long id, String title, String content, String imagePath, String userName, int likeCount, String createdDate, List<GetNoticeCommentResponse> comments) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.imagePath = imagePath;
        this.userName = userName;
        this.likeCount = likeCount;
        this.createdDate = createdDate;
        this.comments = comments;
    }

    public static GetNoticeResponse fromEntity(Notice notice) {
        List<GetNoticeCommentResponse> commentResponses = notice.getComments().stream()
                .map(GetNoticeCommentResponse::fromEntity)
                .collect(Collectors.toList());

        return new GetNoticeResponse(
                notice.getId(),
                notice.getTitle(),
                notice.getContent(),
                notice.getImagePath(),
                notice.getUser().getName(),
                notice.getLikes().size(),
                notice.getCreatedDate(),
                commentResponses
        );
    }


}
