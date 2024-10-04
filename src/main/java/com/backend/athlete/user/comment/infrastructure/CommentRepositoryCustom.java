package com.backend.athlete.user.comment.infrastructure;

import com.backend.athlete.user.comment.domain.Comment;

import java.util.Optional;

public interface CommentRepositoryCustom {
    Optional<Comment> findByIdWithReplies(Long id);

}
