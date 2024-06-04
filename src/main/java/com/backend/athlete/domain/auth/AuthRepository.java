package com.backend.athlete.domain.auth;

import com.backend.athlete.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);

}
