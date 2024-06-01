package com.backend.athlete.domain.notice.repository;

import com.backend.athlete.domain.notice.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByNoticeId(Long id);
}
