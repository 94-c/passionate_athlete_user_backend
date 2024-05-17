package com.backend.athlete.domain.user.repository;

import com.backend.athlete.domain.user.model.Role;
import com.backend.athlete.domain.user.model.type.UserRoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(UserRoleType name);

}
