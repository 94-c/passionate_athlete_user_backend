package com.backend.athlete.user.branch.infrastructure;

import com.backend.athlete.user.branch.domain.Branch;
import com.backend.athlete.user.branch.domain.QBranch;
import com.backend.athlete.user.user.domain.QUser;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BranchRepositoryImpl implements BranchRepositoryCustom {

    private final JPAQueryFactory factory;

    @Override
    public Optional<Branch> findByNameContaining(String name) {
        return Optional.ofNullable(factory.selectFrom(QBranch.branch)
                .where(QBranch.branch.name.containsIgnoreCase(name))
                .fetchOne());
    }

    @Override
    public Page<Branch> findByNameContainingAndManagerNameContaining(String name, String managerName, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(QBranch.branch.name.containsIgnoreCase(name));
        if (managerName != null) {
            builder.and(QUser.user.name.containsIgnoreCase(managerName));
        }

        List<Branch> branches = factory.selectFrom(QBranch.branch)
                .leftJoin(QBranch.branch.manager, QUser.user)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long count = factory.selectFrom(QBranch.branch)
                .leftJoin(QBranch.branch.manager, QUser.user)
                .where(builder)
                .fetchCount();

        return new PageImpl<>(branches, pageable, count);
    }

}
