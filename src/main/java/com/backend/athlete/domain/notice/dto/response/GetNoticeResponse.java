package com.backend.athlete.domain.notice.dto.response;

import com.backend.athlete.domain.file.domain.File;
import com.backend.athlete.domain.notice.domain.Notice;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class GetNoticeResponse {
    private Long id;
    private String kind; // 게시판 종류 추가
    private Long userId;
    private String userName;
    private String title;
    private String content;
    private int likeCount;
    private int commentCount;
    private boolean status;
    private String createdDate;

    public GetNoticeResponse(Long id, String kind, Long userId, String userName, String title, String content, int likeCount, int commentCount, boolean status, String createdDate) {
        this.id = id;
        this.kind = kind;
        this.userId = userId;
        this.userName = userName;
        this.title = title;
        this.content = content;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.status = status;
        this.createdDate = createdDate;
    }

    public static GetNoticeResponse fromEntity(Notice notice, int likeCount, int commentCount) {
        return new GetNoticeResponse(
                notice.getId(),
                notice.getKind().getType(),
                notice.getUser().getId(),
                notice.getUser().getName(),
                notice.getTitle(),
                notice.getContent(),
                likeCount,
                commentCount,
                notice.isStatus(),
                notice.getCreatedDate()
        );
    }
}

