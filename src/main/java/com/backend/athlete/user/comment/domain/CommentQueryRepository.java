package com.backend.athlete.user.comment.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentQueryRepository {
    @Query("SELECT c FROM Comment c LEFT JOIN FETCH c.replies WHERE c.notice.id = :noticeId")
    Page<Comment> findByNoticeIdWithReplies(@Param("noticeId") Long noticeId, Pageable pageable);
}

