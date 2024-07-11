package com.backend.athlete.domain.notice.infrastructure;

import com.backend.athlete.domain.notice.domain.Notice;
import com.backend.athlete.domain.notice.domain.NoticeType;
import com.backend.athlete.domain.notice.domain.QNotice;
import com.backend.athlete.domain.notice.domain.QNoticeType;
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

    private BooleanBuilder toBooleanBuilder(String name, String title, Long kindId) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (name != null && !name.isEmpty()) {
            booleanBuilder.and(QNotice.notice.user.name.containsIgnoreCase(name));
        }
        if (title != null && !title.isEmpty()) {
            booleanBuilder.and(QNotice.notice.title.containsIgnoreCase(title));
        }
        if (kindId != null && kindId != 0) {
            NoticeType kind = factory.selectFrom(QNoticeType.noticeType)
                    .where(QNoticeType.noticeType.id.eq(kindId))
                    .fetchOne();
            if (kind != null) {
                booleanBuilder.and(QNotice.notice.kind.eq(kind));
            }
        }
        return booleanBuilder;
    }


    @Override
    public Page<Notice> findAllByUserAndTitleAndKindAndStatus(String name, String title, Pageable pageable, Long kindId, boolean status) {
        BooleanBuilder booleanBuilder = toBooleanBuilder(name, title, kindId);

        List<Notice> content = factory.selectFrom(QNotice.notice)
                .where(booleanBuilder.and(QNotice.notice.status.eq(status)))
                .orderBy(QNotice.notice.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long count = factory.select(QNotice.notice.count())
                .from(QNotice.notice)
                .where(booleanBuilder.and(QNotice.notice.status.eq(status)))
                .fetchOne();

        return PageableExecutionUtils.getPage(content, pageable, () -> count);
    }
}


