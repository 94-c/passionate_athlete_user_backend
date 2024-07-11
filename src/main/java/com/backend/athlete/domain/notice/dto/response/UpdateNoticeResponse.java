package com.backend.athlete.domain.notice.dto.response;

import com.backend.athlete.domain.notice.domain.Notice;
import com.backend.athlete.domain.user.domain.User;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class UpdateNoticeResponse {
    private Long id;
    private String title;
    private String content;
    private String imagePath;
    private String userName;
    private List<GetNoticeCommentResponse> comments;

    public UpdateNoticeResponse(Long id, String title, String content, String imagePath, String userName, List<GetNoticeCommentResponse> comments) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.imagePath = imagePath;
        this.userName = userName;
        this.comments = comments;
    }

    public static UpdateNoticeResponse fromEntity(Notice notice) {
        User findUser = notice.getUser();
        List<GetNoticeCommentResponse> commentResponses = notice.getComments().stream()
                .map(GetNoticeCommentResponse::fromEntity)
                .collect(Collectors.toList());

        return new UpdateNoticeResponse(
                notice.getId(),
                notice.getTitle(),
                notice.getContent(),
                notice.getImagePath(),
                findUser.getName(),
                commentResponses
        );
    }
}
