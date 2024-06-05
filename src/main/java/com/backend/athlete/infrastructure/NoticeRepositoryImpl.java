package com.backend.athlete.infrastructure;

import com.backend.athlete.domain.notice.Notice;
import com.backend.athlete.domain.notice.QNotice;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoticeRepositoryImpl implements NoticeRepositoryCustom {

    private final JPAQueryFactory factory;
    private BooleanBuilder toBooleanBuilder(String name, String title, Integer kind) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (name != null && !name.isEmpty()) {
            booleanBuilder.and(QNotice.notice.user.name.containsIgnoreCase(name));
        }
        if (title != null && !title.isEmpty()) {
            booleanBuilder.and(QNotice.notice.title.containsIgnoreCase(title));
        }
        if (kind != null) {
            booleanBuilder.and(QNotice.notice.kind.eq(kind));
        }
        return booleanBuilder;
    }

    @Override
    public Page<Notice> findAllByUserAndTitleAndKind(String name, String title, Pageable pageable, Integer kind) {
        BooleanBuilder booleanBuilder = toBooleanBuilder(name, title, kind);

        List<Notice> content = factory.selectFrom(QNotice.notice)
                .where(booleanBuilder)
                .orderBy(QNotice.notice.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long count = factory.select(QNotice.notice.count())
                .from(QNotice.notice)
                .where(booleanBuilder)
                .fetchOne();

        return PageableExecutionUtils.getPage(content, pageable, () -> count);
    }
}
