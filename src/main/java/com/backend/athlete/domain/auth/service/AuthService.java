package com.backend.athlete.domain.auth.service;

import com.backend.athlete.domain.auth.dto.LoginRequest;
import com.backend.athlete.domain.auth.dto.SignUpRequest;
import com.backend.athlete.domain.auth.repository.AuthRepository;
import com.backend.athlete.domain.user.domain.Role;
import com.backend.athlete.domain.user.domain.User;
import com.backend.athlete.domain.user.domain.enums.RoleType;
import com.backend.athlete.global.util.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class AuthService {

    private AuthRepository authRepository;
    private PasswordEncoder passwordEncoder;

    public AuthService(AuthRepository authRepository, PasswordEncoder passwordEncoder) {
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(LoginRequest request) {
        String password = request.password();

        Optional<User> existingUser = authRepository.findByUserId(request.userId());

        if (existingUser.isPresent()) {
            throw new RuntimeException("존재 하지 않는 사용자 입니다.");
        }

        return null;
    }

    public void signUp(SignUpRequest request) {
        Optional<User> existingUser = authRepository.findByUserId(request.userId());

        if (existingUser.isPresent()) {
            throw new RuntimeException("이미 존재하는 사용자입니다.");
        }

        Role userRole = createUserRole();

        User newUser = User.builder()
                .userId(request.userId())
                .name(request.name())
                .password(BCrypt.hashpw(request.password(), BCrypt.gensalt()))
                .roles(Collections.singleton(userRole))
                .build();

        authRepository.save(newUser);
    }

    private Role createUserRole() {
        return Role.builder().roleName(RoleType.USER).build();
    }

}
