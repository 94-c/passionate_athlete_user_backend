package com.backend.athlete.user.notice.application;

import com.backend.athlete.user.notice.domain.Like;
import com.backend.athlete.user.notice.domain.LikeRepository;
import com.backend.athlete.user.notice.domain.Notice;
import com.backend.athlete.user.user.domain.User;
import com.backend.athlete.user.notice.dto.response.CreateLikeNoticeResponse;
import com.backend.athlete.user.notice.dto.response.DeleteLikeNoticeResponse;
import com.backend.athlete.user.notice.dto.response.GetLikeNoticeResponse;
import com.backend.athlete.support.exception.NotFoundException;
import com.backend.athlete.user.auth.jwt.service.CustomUserDetailsImpl;
import com.backend.athlete.support.util.FindUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeNoticeService {
    private final LikeRepository likeRepository;

    @Transactional
    public CreateLikeNoticeResponse likeNotice(Long noticeId, CustomUserDetailsImpl userPrincipal) {
        User user = FindUtils.findByUserId(userPrincipal.getUsername());
        Notice notice = FindUtils.findByNoticeId(noticeId);

        if (likeRepository.existsByUserAndNotice(user, notice)) {
            throw new NotFoundException("이미 좋아요를 누르셨습니다.", HttpStatus.NOT_FOUND);
        }

        Like like = new Like(user, notice);
        likeRepository.save(like);

        Long likeCount = likeRepository.countByNotice(notice);

        return CreateLikeNoticeResponse.fromEntity(like, likeCount);

    }

    public DeleteLikeNoticeResponse unlikeNotice(Long noticeId, CustomUserDetailsImpl userPrincipal) {
        User user = FindUtils.findByUserId(userPrincipal.getUsername());
        Notice notice = FindUtils.findByNoticeId(noticeId);

        if (!likeRepository.existsByUserAndNotice(user, notice)) {
            throw new NotFoundException("Not liked yet", HttpStatus.NOT_FOUND);
        }

        likeRepository.deleteByUserAndNotice(user, notice);

        Long likeCount = likeRepository.countByNotice(notice);

        return DeleteLikeNoticeResponse.fromEntity(notice, user, likeCount);
    }

    public GetLikeNoticeResponse getLikeCount(Long noticeId) {
        Notice notice = FindUtils.findByNoticeId(noticeId);
        long like = likeRepository.countByNotice(notice);
        return GetLikeNoticeResponse.fromEntity(notice, like);
    }

}
