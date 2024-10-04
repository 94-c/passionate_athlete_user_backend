package com.backend.athlete.user.auth.domain;

import com.backend.athlete.user.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);
    boolean existsByUserId(String userId);
    boolean existsByPhoneNumber(String phone);
    Optional<User> findByNameAndPhoneNumberAndBirthDate(String name, String phoneNumber, String birthDate);
}
