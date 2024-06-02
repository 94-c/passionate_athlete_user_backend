package com.backend.athlete.domain.notice.repository;

import com.backend.athlete.domain.notice.model.Like;
import com.backend.athlete.domain.notice.model.Notice;
import com.backend.athlete.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    int countByNoticeId(Long id);
    boolean existsByUserAndNotice(User user, Notice notice);
    void deleteByUserAndNotice(User user, Notice notice);
    long countByNotice(Notice notice);

}
