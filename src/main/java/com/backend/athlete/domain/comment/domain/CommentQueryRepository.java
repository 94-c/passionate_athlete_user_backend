package com.backend.athlete.domain.comment.domain;

import com.backend.athlete.domain.comment.infrastructure.CommentRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentQueryRepository {
    @Query("SELECT c FROM Comment c LEFT JOIN FETCH c.replies WHERE c.notice.id = :noticeId")
    Page<Comment> findByNoticeIdWithReplies(@Param("noticeId") Long noticeId, Pageable pageable);
}

