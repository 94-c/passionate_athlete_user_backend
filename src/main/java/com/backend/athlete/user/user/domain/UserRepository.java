package com.backend.athlete.user.user.domain;

import com.backend.athlete.user.user.infrastructure.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    User findByUserId(String userId);

}
