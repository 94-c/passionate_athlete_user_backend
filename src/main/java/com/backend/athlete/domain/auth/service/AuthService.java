package com.backend.athlete.domain.auth.service;

import com.backend.athlete.domain.auth.dto.LoginRequest;
import com.backend.athlete.domain.auth.dto.SignUpRequest;
import com.backend.athlete.domain.auth.repository.AuthRepository;
import com.backend.athlete.domain.auth.repository.RoleRepository;
import com.backend.athlete.domain.user.domain.Role;
import com.backend.athlete.domain.user.domain.User;
import com.backend.athlete.domain.user.domain.enums.RoleType;
import com.backend.athlete.global.exception.AppException;
import com.backend.athlete.global.exception.BadRequestException;
import com.backend.athlete.global.payload.ApiResponse;
import com.backend.athlete.global.util.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    private final AuthRepository authRepository;
    private final RoleRepository roleRepository;

    public AuthService(AuthRepository authRepository, RoleRepository roleRepository) {
        this.authRepository = authRepository;
        this.roleRepository = roleRepository;
    }

    public User signUp(SignUpRequest request) {
       if (authRepository.existsByUserId(request.userId())) {
           ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "User ID is already taken");
           throw new BadRequestException(apiResponse);
       }

        List<Role> roles = new ArrayList<>();
       roles.add(
            roleRepository.findByRoleName(RoleType.USER)
                    .orElseThrow(() -> new AppException("User Role not Set"))
       );

        User newUser = User.builder()
                .userId(request.userId())
                .name(request.name())
                .password(BCrypt.hashpw(request.password(), BCrypt.gensalt()))
                .roles(roles)
                .build();

        return authRepository.save(newUser);

    }

}
