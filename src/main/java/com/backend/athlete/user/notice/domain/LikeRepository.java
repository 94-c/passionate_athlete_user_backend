package com.backend.athlete.user.notice.domain;

import com.backend.athlete.user.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    int countByNoticeId(Long id);
    boolean existsByUserAndNotice(User user, Notice notice);
    void deleteByUserAndNotice(User user, Notice notice);
    long countByNotice(Notice notice);
}
