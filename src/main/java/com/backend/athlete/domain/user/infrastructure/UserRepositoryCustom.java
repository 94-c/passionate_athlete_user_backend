package com.backend.athlete.domain.user.infrastructure;

import com.backend.athlete.domain.branch.Branch;
import com.backend.athlete.domain.user.domain.User;
import com.backend.athlete.domain.user.domain.type.UserRoleType;

import java.util.List;

public interface UserRepositoryCustom {
    List<User> findByBranchAndRole(Branch branch, UserRoleType role);
}
