package com.backend.athlete.domain.notice.repository;

import com.backend.athlete.domain.notice.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
