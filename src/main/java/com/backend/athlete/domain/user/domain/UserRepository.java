package com.backend.athlete.domain.user.domain;

import com.backend.athlete.domain.user.infrastructure.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    User findByUserId(String userId);

}
