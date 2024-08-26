package com.backend.athlete.domain.notice.application;

import com.backend.athlete.domain.comment.domain.CommentRepository;
import com.backend.athlete.domain.notice.domain.*;
import com.backend.athlete.domain.user.domain.User;
import com.backend.athlete.domain.notice.dto.request.PageSearchNoticeRequest;
import com.backend.athlete.domain.notice.dto.request.CreateNoticeRequest;
import com.backend.athlete.domain.notice.dto.request.UpdateNoticeRequest;
import com.backend.athlete.domain.notice.dto.response.GetNoticeResponse;
import com.backend.athlete.domain.notice.dto.response.CreateNoticeResponse;
import com.backend.athlete.domain.notice.dto.response.UpdateNoticeResponse;
import com.backend.athlete.support.exception.NotFoundException;
import com.backend.athlete.domain.auth.jwt.service.CustomUserDetailsImpl;
import com.backend.athlete.support.util.FindUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final NoticeTypeRepository noticeTypeRepository;
    @Transactional(readOnly = true)
    public Page<GetNoticeResponse> searchNotices(PageSearchNoticeRequest request, int page, int perPage, Long kindId, boolean status) {
        Pageable pageable = PageRequest.of(page, perPage);
        Page<Notice> notices = noticeRepository.findAllByUserAndTitleAndKindAndStatus(request.getTitle(), request.getName(), pageable, kindId, status);

        return notices.map(notice -> {
            int likeCount = likeRepository.countByNoticeId(notice.getId());
            int likeComment = commentRepository.countByNoticeId(notice.getId());
            return GetNoticeResponse.fromEntity(notice, likeCount, likeComment);
        });
    }

    @Transactional(readOnly = true)
    public Page<GetNoticeResponse> getNoticesByType(Long kindId, int page, int size, boolean status) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Notice> notices = noticeRepository.findAllByKindId(kindId, pageable, status);

        return notices.map(notice -> {
            int likeCount = likeRepository.countByNoticeId(notice.getId());
            int commentCount = commentRepository.countByNoticeId(notice.getId());
            return GetNoticeResponse.fromEntity(notice, likeCount, commentCount);
        });
    }

    public CreateNoticeResponse saveNotice(CustomUserDetailsImpl userPrincipal, CreateNoticeRequest noticeRequest) {
        User user = FindUtils.findByUserId(userPrincipal.getUsername());
        NoticeType kind = noticeTypeRepository.findById(noticeRequest.getKindId()).orElseThrow(() -> new NotFoundException("Invalid notice type", HttpStatus.NOT_FOUND));

        Notice notice = new Notice(noticeRequest.getTitle(), noticeRequest.getContent(), kind, noticeRequest.isStatus(), user);
        Notice savedNotice = noticeRepository.save(notice);

        return CreateNoticeResponse.fromEntity(savedNotice);
    }
    @Transactional
    public GetNoticeResponse getNotice(Long id) {
        Notice notice = FindUtils.findByNoticeId(id);
        int likeCount = likeRepository.countByNoticeId(notice.getId());
        int likeComment = commentRepository.countByNoticeId(notice.getId());
        return GetNoticeResponse.fromEntity(notice, likeCount, likeComment);
    }

    @Transactional
    public UpdateNoticeResponse updateNotice(Long id, CustomUserDetailsImpl userPrincipal, UpdateNoticeRequest noticeRequest) {
        Notice notice = FindUtils.findByNoticeId(id);

        if (!notice.getUser().getUserId().equals(userPrincipal.getUsername())) {
            throw new NotFoundException("게시글의 권한이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }

        notice.updateNotice(noticeRequest.getTitle(), noticeRequest.getContent());

        Notice updatedNotice = noticeRepository.save(notice);

        return UpdateNoticeResponse.fromEntity(updatedNotice);
    }

    public void deleteNotice(Long id, CustomUserDetailsImpl userPrincipal) {
        Notice notice = FindUtils.findByNoticeId(id);

        if (!notice.getUser().getUserId().equals(userPrincipal.getUsername())) {
            throw new NotFoundException("이 게시물를 삭제할 권한이 없습니다.", HttpStatus.NOT_FOUND);
        }

        notice.deletedNotice(LocalDateTime.now());
        noticeRepository.save(notice);
    }

}
