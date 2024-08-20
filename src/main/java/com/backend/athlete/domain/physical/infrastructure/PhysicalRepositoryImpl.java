package com.backend.athlete.domain.physical.infrastructure;

import com.backend.athlete.domain.branch.domain.QBranch;
import com.backend.athlete.domain.physical.domain.Physical;
import com.backend.athlete.domain.physical.domain.QPhysical;
import com.backend.athlete.domain.user.domain.QUser;
import com.backend.athlete.domain.user.domain.type.UserGenderType;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PhysicalRepositoryImpl implements PhysicalRepositoryCustom {

    private final JPAQueryFactory factory;

    private BooleanBuilder toBooleanBuilder(LocalDate startDate, LocalDate endDate, String gender) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QPhysical physical = QPhysical.physical;

        if (startDate != null) {
            booleanBuilder.and(physical.measureDate.goe(startDate.atStartOfDay()));
        }
        if (endDate != null) {
            booleanBuilder.and(physical.measureDate.loe(endDate.atTime(23, 59, 59)));
        }
        if (gender != null && !gender.isEmpty()) {
            booleanBuilder.and(physical.user.gender.eq(UserGenderType.valueOf(gender.toUpperCase())));
        }

        return booleanBuilder;
    }

    @Override
    public List<Physical> findPhysicalRankings(LocalDate startDate, LocalDate endDate, String gender, int limit) {
        QPhysical physical = QPhysical.physical;
        QUser user = QUser.user;
        QBranch branch = QBranch.branch;

        BooleanBuilder booleanBuilder = toBooleanBuilder(startDate, endDate, gender);

        QPhysical subPhysical = new QPhysical("subPhysical");

        JPQLQuery<Long> subquery = JPAExpressions
                .select(subPhysical.id.max())
                .from(subPhysical)
                .where(subPhysical.user.id.eq(physical.user.id)
                        .and(subPhysical.measureDate.between(startDate.atStartOfDay(), endDate.atTime(23, 59, 59))));

        return factory.selectFrom(physical)
                .join(physical.user, user)
                .join(user.branch, branch)
                .where(booleanBuilder)
                .where(physical.id.in(subquery))
                .orderBy(physical.bodyFatMassChange.asc())
                .limit(limit)
                .fetch();
    }
}

