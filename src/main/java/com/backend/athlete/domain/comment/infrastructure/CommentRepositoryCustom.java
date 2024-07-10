package com.backend.athlete.domain.comment.infrastructure;

import com.backend.athlete.domain.comment.domain.Comment;

import java.util.Optional;

public interface CommentRepositoryCustom {
    Optional<Comment> findByIdWithReplies(Long id);

}
