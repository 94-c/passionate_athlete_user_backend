package com.backend.athlete.user.comment.domain;

import com.backend.athlete.user.comment.infrastructure.CommentRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom, CommentQueryRepository {
    List<Comment> findByNoticeId(Long id);
    Optional<Comment> findByIdAndParentId(Long id, Long parentId);
    Page<Comment> findByParent(Comment parent, Pageable pageable);
    Optional<Comment> findByIdAndNoticeId(Long id, Long noticeId);
    int countByNoticeId(Long id);
}

