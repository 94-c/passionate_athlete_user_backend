package com.backend.athlete.domain.notice.service;

import com.backend.athlete.domain.notice.dto.request.SaveNoticeRequest;
import com.backend.athlete.domain.notice.dto.response.SaveNoticeResponse;
import com.backend.athlete.domain.notice.model.Notice;
import com.backend.athlete.domain.notice.repository.CommentRepository;
import com.backend.athlete.domain.notice.repository.LikeRepository;
import com.backend.athlete.domain.notice.repository.NoticeRepository;
import com.backend.athlete.domain.user.model.User;
import com.backend.athlete.domain.user.repository.UserRepository;
import com.backend.athlete.global.jwt.service.CustomUserDetailsImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;

    private final Path rootLocation = Paths.get("notice-images");

    public NoticeService(NoticeRepository noticeRepository, CommentRepository commentRepository, LikeRepository likeRepository, UserRepository userRepository) {
        this.noticeRepository = noticeRepository;
        this.commentRepository = commentRepository;
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
    }

    public SaveNoticeResponse saveNotice(CustomUserDetailsImpl userPrincipal, SaveNoticeRequest noticeRequest, MultipartFile file) throws IOException {
        User findUser = userRepository.findByUserId(userPrincipal.getUsername());

        if (!file.isEmpty()) {
            if (Files.notExists(rootLocation)) {
                Files.createDirectories(rootLocation);
            }
            String filename = file.getOriginalFilename();
            Files.copy(file.getInputStream(), rootLocation.resolve(filename));
            noticeRequest.setImagePath(rootLocation.resolve(filename).toString());
        }

        Notice notice = SaveNoticeRequest.toEntity(noticeRequest, findUser);
        Notice savedNotice = noticeRepository.save(notice);

        return SaveNoticeResponse.fromEntity(savedNotice);
    }

    public Notice getNotice(Long id) {
        return noticeRepository.findById(id).orElseThrow(() -> new RuntimeException("Notice not found"));
    }

    public List<Notice> getAllNotices() {
        return noticeRepository.findAll();
    }

    public Comment addComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public Like addLike(Like like) {
        return likeRepository.save(like);
    }
}
}
