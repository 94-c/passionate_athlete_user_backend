package com.backend.athlete.user.user.infrastructure;

import com.backend.athlete.user.branch.domain.Branch;
import com.backend.athlete.user.user.domain.QRole;
import com.backend.athlete.user.user.domain.QUser;
import com.backend.athlete.user.user.domain.User;
import com.backend.athlete.user.user.domain.type.UserRoleType;
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
