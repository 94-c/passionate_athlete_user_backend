package com.backend.athlete.domain.comment.infrastructure;

import com.backend.athlete.domain.comment.domain.Comment;
import com.backend.athlete.domain.comment.domain.QComment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory factory;

    @Override
    public Optional<Comment> findByIdWithReplies(Long id) {
        Comment fetchedComment = factory.selectFrom(QComment.comment)
                .leftJoin(QComment.comment.replies).fetchJoin()
                .where(QComment.comment.id.eq(id))
                .fetchOne();

        return Optional.ofNullable(fetchedComment);
    }
}
