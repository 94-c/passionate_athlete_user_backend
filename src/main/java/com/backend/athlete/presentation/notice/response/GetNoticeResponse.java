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
    private Long userId;
    private String userName;
    private int likeCount;
    private boolean status;
    private String createdDate;
    private String modifiedDate;
    private List<GetNoticeCommentResponse> comments;

    public GetNoticeResponse(Long id, String title, String content, String imagePath, Long userId, String userName, int likeCount, boolean status, String createdDate, String modifiedDate, List<GetNoticeCommentResponse> comments) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.imagePath = imagePath;
        this.userId = userId;
        this.userName = userName;
        this.likeCount = likeCount;
        this.status = status;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
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
                notice.getUser().getId(),
                notice.getUser().getName(),
                notice.getLikes().size(),
                notice.isStatus(),
                notice.getCreatedDate(),
                notice.getModifiedDate(),
                commentResponses
        );
    }


}
