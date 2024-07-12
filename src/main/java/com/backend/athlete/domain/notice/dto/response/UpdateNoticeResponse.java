package com.backend.athlete.domain.notice.dto.response;

import com.backend.athlete.domain.file.domain.File;
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
    private List<String> imagePaths; // 다중 이미지 경로를 위한 필드
    private String userName;
    private List<GetNoticeCommentResponse> comments;

    public UpdateNoticeResponse(Long id, String title, String content, List<String> imagePaths, String userName, List<GetNoticeCommentResponse> comments) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.imagePaths = imagePaths;
        this.userName = userName;
        this.comments = comments;
    }

    public static UpdateNoticeResponse fromEntity(Notice notice) {
        User findUser = notice.getUser();
        List<GetNoticeCommentResponse> commentResponses = notice.getComments().stream()
                .map(GetNoticeCommentResponse::fromEntity)
                .collect(Collectors.toList());

        List<String> imagePaths = notice.getFiles().stream()
                .map(File::getFilePath)
                .collect(Collectors.toList());

        return new UpdateNoticeResponse(
                notice.getId(),
                notice.getTitle(),
                notice.getContent(),
                imagePaths,
                findUser.getName(),
                commentResponses
        );
    }
}
