package com.backend.athlete.user.user.infrastructure;

import com.backend.athlete.user.branch.domain.Branch;
import com.backend.athlete.user.user.domain.User;
import com.backend.athlete.user.user.domain.type.UserRoleType;

import java.util.List;

public interface UserRepositoryCustom {
    List<User> findByBranchAndRole(Branch branch, UserRoleType role);
}
