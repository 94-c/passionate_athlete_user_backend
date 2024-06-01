package com.backend.athlete.domain.notice.dto.response;

import com.backend.athlete.domain.notice.model.Notice;
import com.backend.athlete.domain.user.model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
public class UpdateNoticeResponse {
    private Long id;
    private String title;
    private String content;
    private String imagePath;
    private String userName;
    private int likeCount;
    private List<GetCommentResponse> comments;

    public UpdateNoticeResponse(Long id, String title, String content, String imagePath, String userName, int likeCount, List<GetCommentResponse> comments) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.imagePath = imagePath;
        this.userName = userName;
        this.likeCount = likeCount;
        this.comments = comments;
    }

    public static UpdateNoticeResponse fromEntity(Notice notice) {
        User findUser = notice.getUser();
        List<GetCommentResponse> commentResponses = notice.getComments().stream()
                .map(GetCommentResponse::fromEntity)
                .collect(Collectors.toList());

        return new UpdateNoticeResponse(
                notice.getId(),
                notice.getTitle(),
                notice.getContent(),
                notice.getImagePath(),
                findUser.getName(),
                notice.getLikes().size(),
                commentResponses
        );
    }
}