package com.backend.athlete.domain.notice.infrastructure;

import com.backend.athlete.domain.notice.domain.Notice;
import com.backend.athlete.domain.notice.domain.NoticeType;
import com.backend.athlete.domain.notice.domain.QNotice;
import com.backend.athlete.domain.notice.domain.QNoticeType;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoticeRepositoryImpl implements NoticeRepositoryCustom {
    private final JPAQueryFactory factory;

    private BooleanBuilder toBooleanBuilder(String name, String title, Long kindId, Boolean status) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (title != null && !title.isEmpty()) {
            booleanBuilder.and(QNotice.notice.title.containsIgnoreCase(title));
        }
        if (name != null && !name.isEmpty()) {
            booleanBuilder.and(QNotice.notice.user.name.containsIgnoreCase(name));
        }
        if (kindId != null && kindId != 0) {
            BooleanExpression kindExpression = QNoticeType.noticeType.id.eq(kindId);
            NoticeType kind = factory.selectFrom(QNoticeType.noticeType)
                    .where(kindExpression)
                    .fetchOne();
            if (kind != null) {
                booleanBuilder.and(QNotice.notice.kind.eq(kind));
            }
        }
        if (status != null) {
            booleanBuilder.and(QNotice.notice.status.eq(status));
        }
        booleanBuilder.and(QNotice.notice.deletedAt.isNull()); // Only include non-deleted notices

        return booleanBuilder;
    }

    @Override
    public Page<Notice> findAllByUserAndTitleAndKindAndStatus(String name, String title, Pageable pageable, Long kindId, boolean status) {
        BooleanBuilder booleanBuilder = toBooleanBuilder(name, title, kindId, status);

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

    @Override
    public Page<Notice> findAllByKindId(Long kindId, Pageable pageable, boolean status) {
        BooleanBuilder booleanBuilder = toBooleanBuilder(null, null, kindId, status);

        long total = factory.select(QNotice.notice.count())
                .from(QNotice.notice)
                .where(booleanBuilder)
                .fetchOne();

        if (total == 0) {
            return new PageImpl<>(Collections.emptyList(), pageable, total);
        }

        List<Notice> content = factory.selectFrom(QNotice.notice)
                .where(booleanBuilder)
                .orderBy(QNotice.notice.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(content, pageable, () -> total);
    }
}


