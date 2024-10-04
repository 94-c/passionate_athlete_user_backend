package com.backend.athlete.user.user.domain;

import com.backend.athlete.user.user.domain.type.UserRoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(UserRoleType name);

}
