package com.backend.athlete.infrastructure;

import com.backend.athlete.domain.branch.Branch;
import com.backend.athlete.domain.user.User;
import com.backend.athlete.domain.user.type.UserRoleType;

import java.util.List;

public interface UserRepositoryCustom {
    List<User> findByBranchAndRole(Branch branch, UserRoleType role);
}
