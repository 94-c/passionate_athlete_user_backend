package com.backend.athlete.domain.notice.dto.response;

import com.backend.athlete.domain.file.domain.File;
import com.backend.athlete.domain.notice.domain.Notice;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class GetNoticeResponse {
    private Long id;
    private String title;
    private String content;
    private List<String> imagePaths; // 다중 이미지 경로를 위한 필드
    private Long userId;
    private String userName;
    private int likeCount;
    private boolean status;
    private String createdDate;
    private String modifiedDate;
    private List<GetNoticeCommentResponse> comments;

    public GetNoticeResponse(Long id, String title, String content, List<String> imagePaths, Long userId, String userName, int likeCount, boolean status, String createdDate, String modifiedDate, List<GetNoticeCommentResponse> comments) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.imagePaths = imagePaths;
        this.userId = userId;
        this.userName = userName;
        this.likeCount = likeCount;
        this.status = status;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.comments = comments;
    }

    public static GetNoticeResponse fromEntity(Notice notice, int likeCount) {
        List<GetNoticeCommentResponse> commentResponses = notice.getComments().stream()
                .map(GetNoticeCommentResponse::fromEntity)
                .collect(Collectors.toList());

        List<String> imagePaths = notice.getFiles().stream()
                .map(File::getFilePath)
                .collect(Collectors.toList());

        return new GetNoticeResponse(
                notice.getId(),
                notice.getTitle(),
                notice.getContent(),
                imagePaths,
                notice.getUser().getId(),
                notice.getUser().getName(),
                likeCount,
                notice.isStatus(),
                notice.getCreatedDate(),
                notice.getModifiedDate(),
                commentResponses
        );
    }
}
