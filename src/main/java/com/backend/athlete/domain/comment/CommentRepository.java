package com.backend.athlete.domain.comment;

import com.backend.athlete.infrastructure.CommentRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {
    List<Comment> findByNoticeId(Long id);
    @Query("SELECT c FROM Comment c LEFT JOIN FETCH c.replies WHERE c.notice.id = :noticeId")
    Page<Comment> findByNoticeIdWithReplies(@Param("noticeId") Long noticeId, Pageable pageable);
    Optional<Comment> findByIdAndParentId(Long id, Long parentId);
    Page<Comment> findByParent(Comment parent, Pageable pageable);
    Optional<Comment> findByIdAndNoticeId(Long id, Long noticeId);

}

