package com.backend.athlete.domain.notice.application;

import com.backend.athlete.domain.comment.domain.Comment;
import com.backend.athlete.domain.comment.domain.CommentRepository;
import com.backend.athlete.domain.notice.domain.*;
import com.backend.athlete.domain.user.domain.User;
import com.backend.athlete.domain.notice.dto.request.PageSearchNoticeRequest;
import com.backend.athlete.domain.notice.dto.request.CreateNoticeRequest;
import com.backend.athlete.domain.notice.dto.request.UpdateNoticeRequest;
import com.backend.athlete.domain.notice.dto.response.GetNoticeResponse;
import com.backend.athlete.domain.notice.dto.response.PageSearchNoticeResponse;
import com.backend.athlete.domain.notice.dto.response.CreateNoticeResponse;
import com.backend.athlete.domain.notice.dto.response.UpdateNoticeResponse;
import com.backend.athlete.support.exception.NotFoundException;
import com.backend.athlete.domain.auth.jwt.service.CustomUserDetailsImpl;
import com.backend.athlete.support.util.FileUtils;
import com.backend.athlete.support.util.FindUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final NoticeTypeRepository noticeTypeRepository;

    private final Path rootLocation = Paths.get("notice-images");

    public NoticeService(NoticeRepository noticeRepository, CommentRepository commentRepository, LikeRepository likeRepository, NoticeTypeRepository noticeTypeRepository) {
        this.noticeRepository = noticeRepository;
        this.commentRepository = commentRepository;
        this.likeRepository = likeRepository;
        this.noticeTypeRepository = noticeTypeRepository;
    }

    @Transactional(readOnly = true)
    public Page<PageSearchNoticeResponse> searchNotices(PageSearchNoticeRequest request, int page, int perPage, Long kindId, boolean status) {
        Pageable pageable = PageRequest.of(page, perPage);
        Page<Notice> notices = noticeRepository.findAllByUserAndTitleAndKindAndStatus(request.getName(), request.getTitle(), pageable, kindId, status);

        return notices.map(notice -> {
            int likeCount = likeRepository.countByNoticeId(notice.getId());
            List<Comment> comments = commentRepository.findByNoticeId(notice.getId());
            return PageSearchNoticeResponse.fromEntity(notice, likeCount, comments);
        });
    }

    public CreateNoticeResponse saveNotice(CustomUserDetailsImpl userPrincipal, CreateNoticeRequest noticeRequest, MultipartFile file) throws IOException {
        User user = FindUtils.findByUserId(userPrincipal.getUsername());
        NoticeType kind = noticeTypeRepository.findById(noticeRequest.getKindId()).orElseThrow(() -> new NotFoundException("Invalid notice type", HttpStatus.NOT_FOUND));

        String imagePath = null;
        if (!file.isEmpty()) {
            imagePath = FileUtils.saveFile(file, rootLocation);
        }

        Notice savedNotice = noticeRepository.save(CreateNoticeRequest.toEntity(noticeRequest, user, kind, imagePath));

        return CreateNoticeResponse.fromEntity(savedNotice);
    }

    @Transactional
    public GetNoticeResponse getNotice(Long id) {
        Notice notice = FindUtils.findByNoticeId(id);
        return GetNoticeResponse.fromEntity(notice);
    }

    @Transactional
    public UpdateNoticeResponse updateNotice(Long id, CustomUserDetailsImpl userPrincipal, UpdateNoticeRequest noticeRequest, MultipartFile file) throws IOException {
        Notice notice = FindUtils.findByNoticeId(id);

        if (!notice.getUser().getUserId().equals(userPrincipal.getUsername())) {
            throw new NotFoundException("게시글의 권한이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }

        String imagePath = notice.getImagePath();
        if (!file.isEmpty()) {
            imagePath = FileUtils.saveFile(file, rootLocation);
        }

        notice.updateNotice(noticeRequest.getTitle(), noticeRequest.getContent(), imagePath);

        Notice updatedNotice = noticeRepository.save(notice);

        return UpdateNoticeResponse.fromEntity(updatedNotice);
    }

    public void deleteNotice(Long id, CustomUserDetailsImpl userPrincipal) {
        Notice notice = FindUtils.findByNoticeId(id);

        if (!notice.getUser().getUserId().equals(userPrincipal.getUsername())) {
            throw new NotFoundException("이 게시물를 삭제할 권한이 없습니다.", HttpStatus.NOT_FOUND);
        }

        noticeRepository.delete(notice);
    }

    public GetNoticeResponse setStatus(Long id, CustomUserDetailsImpl userPrincipal) {
        Notice notice = FindUtils.findByNoticeId(id);

        if (!notice.getUser().getUserId().equals(userPrincipal.getUsername())) {
            throw new NotFoundException("이 게시물를 삭제할 권한이 없습니다.", HttpStatus.NOT_FOUND);
        }

        notice.setStatus(true);

        Notice setStatusNotice = noticeRepository.save(notice);

        return GetNoticeResponse.fromEntity(setStatusNotice);
    }
}
