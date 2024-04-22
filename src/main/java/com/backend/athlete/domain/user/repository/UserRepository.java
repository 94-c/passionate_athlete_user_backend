package com.backend.athlete.domain.user.repository;

import com.backend.athlete.domain.user.domain.User;
import com.backend.athlete.global.exception.ResourceNotFoundException;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(@NotBlank String userId);

    default User getUserByUserId(String userId) {
        return findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
    }
}
