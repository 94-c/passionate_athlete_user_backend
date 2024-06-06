package com.backend.athlete.domain.user;

import com.backend.athlete.domain.branch.Branch;
import com.backend.athlete.domain.user.type.UserRoleType;
import com.backend.athlete.infrastructure.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    User findByUserId(String userId);

}
