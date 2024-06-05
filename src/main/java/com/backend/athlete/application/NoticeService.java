package com.backend.athlete.application;

import com.backend.athlete.domain.comment.Comment;
import com.backend.athlete.domain.comment.CommentRepository;
import com.backend.athlete.domain.notice.LikeRepository;
import com.backend.athlete.domain.notice.Notice;
import com.backend.athlete.domain.notice.NoticeRepository;
import com.backend.athlete.domain.user.User;
import com.backend.athlete.domain.user.UserRepository;
import com.backend.athlete.presentation.notice.request.PageSearchNoticeRequest;
import com.backend.athlete.presentation.notice.request.CreateNoticeRequest;
import com.backend.athlete.presentation.notice.request.UpdateNoticeRequest;
import com.backend.athlete.presentation.notice.response.GetNoticeResponse;
import com.backend.athlete.presentation.notice.response.PageSearchNoticeResponse;
import com.backend.athlete.presentation.notice.response.CreateNoticeResponse;
import com.backend.athlete.presentation.notice.response.UpdateNoticeResponse;
import com.backend.athlete.support.exception.ServiceException;
import com.backend.athlete.support.jwt.service.CustomUserDetailsImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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


    public CreateNoticeResponse saveNotice(CustomUserDetailsImpl userPrincipal, CreateNoticeRequest noticeRequest, MultipartFile file) throws IOException {
        User findUser = userRepository.findByUserId(userPrincipal.getUsername());

        if (!file.isEmpty()) {
            if (Files.notExists(rootLocation)) {
                Files.createDirectories(rootLocation);
            }
            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Files.copy(file.getInputStream(), rootLocation.resolve(filename));
            noticeRequest.setImagePath(rootLocation.resolve(filename).toString());
        }
        Notice savedNotice = noticeRepository.save(CreateNoticeRequest.toEntity(noticeRequest, findUser));

        return CreateNoticeResponse.fromEntity(savedNotice);
    }

    @Transactional
    public GetNoticeResponse getNotice(Long id) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new ServiceException("존재하지 않는 게시물입니다."));

        return GetNoticeResponse.fromEntity(notice);
    }

    @Transactional
    public UpdateNoticeResponse updateNotice(Long id, CustomUserDetailsImpl userPrincipal, UpdateNoticeRequest noticeRequest, MultipartFile file) throws IOException {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new ServiceException("게시글을 찾지 못했습니다."));

        if (!notice.getUser().getUserId().equals(userPrincipal.getUsername())) {
            throw new ServiceException("게시글의 권한이 존재하지 않습니다.");
        }

        String imagePath = notice.getImagePath();
        if (!file.isEmpty()) {
            if (Files.notExists(rootLocation)) {
                Files.createDirectories(rootLocation);
            }
            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Files.copy(file.getInputStream(), rootLocation.resolve(filename));
            imagePath = rootLocation.resolve(filename).toString();
        }

        notice.updateNotice(noticeRequest.getTitle(), noticeRequest.getContent(), imagePath);

        Notice updatedNotice = noticeRepository.save(notice);

        return UpdateNoticeResponse.fromEntity(updatedNotice);
    }

    public void deleteNotice(Long id, CustomUserDetailsImpl userPrincipal) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new ServiceException("회원을 찾지 못했습니다."));

        if (!notice.getUser().getUserId().equals(userPrincipal.getUsername())) {
            throw new ServiceException("이 게시물를 삭제할 권한이 없습니다.");
        }

        noticeRepository.delete(notice);
    }

    public Page<PageSearchNoticeResponse> searchNotices(PageSearchNoticeRequest request, int page, int perPage, Integer kind) {
        Pageable pageable = PageRequest.of(page, perPage);
        Page<Notice> notices = noticeRepository.findAllByUserAndTitle(request.getName(), request.getTitle(), pageable);

        return notices.map(notice -> {
            int likeCount = likeRepository.countByNoticeId(notice.getId());
            List<Comment> comments = commentRepository.findByNoticeId(notice.getId());
            return PageSearchNoticeResponse.fromEntity(notice, likeCount, comments);
        });
    }

}
