package com.backend.athlete.application;

import com.backend.athlete.domain.notice.Like;
import com.backend.athlete.domain.notice.LikeRepository;
import com.backend.athlete.domain.notice.Notice;
import com.backend.athlete.domain.notice.NoticeRepository;
import com.backend.athlete.domain.user.User;
import com.backend.athlete.domain.user.UserRepository;
import com.backend.athlete.presentation.response.CreateLikeNoticeResponse;
import com.backend.athlete.presentation.response.DeleteLikeNoticeResponse;
import com.backend.athlete.presentation.response.GetLikeNoticeResponse;
import com.backend.athlete.support.exception.ServiceException;
import com.backend.athlete.support.jwt.service.CustomUserDetailsImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LikeNoticeService {
    private final LikeRepository likeRepository;
    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository;

    public LikeNoticeService(LikeRepository likeRepository, NoticeRepository noticeRepository, UserRepository userRepository) {
        this.likeRepository = likeRepository;
        this.noticeRepository = noticeRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public CreateLikeNoticeResponse likeNotice(Long noticeId, CustomUserDetailsImpl userPrincipal) {
        User user = userRepository.findByUserId(userPrincipal.getUsername());
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> new ServiceException("게시글이 존재 하지 않습니다."));

        if (likeRepository.existsByUserAndNotice(user, notice)) {
            throw new ServiceException("이미 좋아요를 누르셨습니다.");
        }

        Like like = new Like(user, notice);
        likeRepository.save(like);

        Long likeCount = likeRepository.countByNotice(notice);

        return CreateLikeNoticeResponse.fromEntity(like, likeCount);

    }


    public DeleteLikeNoticeResponse unlikeNotice(Long noticeId, CustomUserDetailsImpl userPrincipal) {
        User user = userRepository.findByUserId(userPrincipal.getUsername());
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> new ServiceException("게시글이 존재 하지 않습니다."));

        if (!likeRepository.existsByUserAndNotice(user, notice)) {
            throw new ServiceException("Not liked yet");
        }

        likeRepository.deleteByUserAndNotice(user, notice);

        Long likeCount = likeRepository.countByNotice(notice);

        return DeleteLikeNoticeResponse.fromEntity(notice, user, likeCount);
    }

    public GetLikeNoticeResponse getLikeCount(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> new ServiceException("게시글이 존재 하지 않습니다."));
        long like = likeRepository.countByNotice(notice);
        return GetLikeNoticeResponse.fromEntity(notice, like);
    }

}
