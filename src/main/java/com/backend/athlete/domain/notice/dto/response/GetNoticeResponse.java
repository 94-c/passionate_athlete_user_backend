package com.backend.athlete.domain.notice.dto.response;

import com.backend.athlete.domain.notice.model.Notice;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
public class GetNoticeResponse {
    private Long id;
    private String title;
    private String content;
    private String imagePath;
    private String userName;
    private int likeCount;
    private List<GetCommentResponse> comments;

    public GetNoticeResponse(Long id, String title, String content, String imagePath, String userName, int likeCount, List<GetCommentResponse> comments) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.imagePath = imagePath;
        this.userName = userName;
        this.likeCount = likeCount;
        this.comments = comments;
    }

    public static GetNoticeResponse fromEntity(Notice notice) {
        List<GetCommentResponse> commentResponses = notice.getComments().stream()
                .map(GetCommentResponse::fromEntity)
                .collect(Collectors.toList());

        return new GetNoticeResponse(
                notice.getId(),
                notice.getTitle(),
                notice.getContent(),
                notice.getImagePath(),
                notice.getUser().getName(),
                notice.getLikes().size(),
                commentResponses
        );
    }


}
