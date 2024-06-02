package com.backend.athlete.domain.notice.service;

import com.backend.athlete.domain.notice.model.Like;
import com.backend.athlete.domain.notice.model.Notice;
import com.backend.athlete.domain.notice.repository.LikeRepository;
import com.backend.athlete.domain.notice.repository.NoticeRepository;
import com.backend.athlete.domain.user.model.User;
import com.backend.athlete.domain.user.repository.UserRepository;
import com.backend.athlete.global.exception.ServiceException;
import com.backend.athlete.global.jwt.service.CustomUserDetailsImpl;
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


    public void likeNotice(Long noticeId, CustomUserDetailsImpl userPrincipal) {
        User user = userRepository.findByUserId(userPrincipal.getUsername());
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> new ServiceException("게시글이 존재 하지 않습니다."));

        if (likeRepository.existsByUserAndNotice(user, notice)) {
            throw new RuntimeException("Already liked");
        }

        Like like = new Like(user, notice);
        likeRepository.save(like);
    }


    public void unlikeNotice(Long noticeId, CustomUserDetailsImpl userPrincipal) {
        User user = userRepository.findByUserId(userPrincipal.getUsername());
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> new ServiceException("게시글이 존재 하지 않습니다."));

        if (!likeRepository.existsByUserAndNotice(user, notice)) {
            throw new RuntimeException("Not liked yet");
        }

        likeRepository.deleteByUserAndNotice(user, notice);
    }

    public long getLikeCount(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> new ServiceException("게시글이 존재 하지 않습니다."));
        return likeRepository.countByNotice(notice);
    }

}
