package com.backend.athlete.infrastructure;

import com.backend.athlete.domain.comment.Comment;

import java.util.Optional;

public interface CommentRepositoryCustom {
    Optional<Comment> findByIdWithReplies(Long id);

}
