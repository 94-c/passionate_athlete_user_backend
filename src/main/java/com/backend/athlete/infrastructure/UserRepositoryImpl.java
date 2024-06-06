package com.backend.athlete.infrastructure;

import com.backend.athlete.domain.branch.Branch;
import com.backend.athlete.domain.user.QRole;
import com.backend.athlete.domain.user.QUser;
import com.backend.athlete.domain.user.User;
import com.backend.athlete.domain.user.type.UserRoleType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory factory;

    @Override
    public List<User> findByBranchAndRole(Branch branch, UserRoleType role) {
        return factory.selectFrom(QUser.user)
                .join(QUser.user.roles, QRole.role)
                .where(QUser.user.branch.eq(branch)
                        .and(QRole.role.name.eq(role)))
                .orderBy(QUser.user.createdDate.desc())
                .fetch();
    }

}
