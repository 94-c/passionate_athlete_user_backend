package com.backend.athlete.user.physical.infrastructure;

import com.backend.athlete.user.branch.domain.QBranch;
import com.backend.athlete.user.physical.domain.Physical;
import com.backend.athlete.user.physical.domain.QPhysical;
import com.backend.athlete.user.user.domain.QUser;
import com.backend.athlete.user.user.domain.type.UserGenderType;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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

    private BooleanExpression notZeroAndNotNaN(NumberPath<Double> path) {
        return path.ne(0.0).and(path.isNotNull());
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
                .where(notZeroAndNotNaN(physical.bodyFatMassChange))
                .orderBy(physical.bodyFatMassChange.asc())
                .limit(limit)
                .fetch()
                .stream()
                .filter(p -> !Double.isNaN(p.getBodyFatMassChange()))
                .collect(Collectors.toList());
    }

}

