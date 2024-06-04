package com.backend.athlete.domain.notice;

import com.backend.athlete.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    int countByNoticeId(Long id);
    boolean existsByUserAndNotice(User user, Notice notice);
    void deleteByUserAndNotice(User user, Notice notice);
    long countByNotice(Notice notice);

}
