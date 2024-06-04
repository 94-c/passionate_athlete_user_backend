package com.backend.athlete.domain.user;

import com.backend.athlete.domain.branch.Branch;
import com.backend.athlete.domain.user.type.UserRoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(String userId);
    @Query("SELECT u FROM User u JOIN u.roles r WHERE u.branch = :branch AND r.name = :role ORDER BY u.createdDate DESC")
    List<User> findByBranchAndRole(@Param("branch") Branch branch, @Param("role") UserRoleType role);

}
