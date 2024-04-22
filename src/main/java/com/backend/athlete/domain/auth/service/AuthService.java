package com.backend.athlete.domain.auth.service;

import com.backend.athlete.domain.auth.dto.LoginRequest;
import com.backend.athlete.domain.auth.dto.SignUpRequest;
import com.backend.athlete.domain.auth.dto.data.LoginTokenData;
import com.backend.athlete.domain.auth.exception.DisabledUserException;
import com.backend.athlete.domain.auth.repository.AuthRepository;
import com.backend.athlete.domain.auth.repository.RoleRepository;
import com.backend.athlete.domain.user.domain.Role;
import com.backend.athlete.domain.user.domain.User;
import com.backend.athlete.domain.user.domain.enums.RoleType;
import com.backend.athlete.domain.user.domain.enums.UserStatus;
import com.backend.athlete.global.exception.AppException;
import com.backend.athlete.global.exception.BadRequestException;
import com.backend.athlete.global.jwt.JwtTokenProvider;
import com.backend.athlete.global.jwt.UserPrincipal;
import com.backend.athlete.global.payload.ApiResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final AuthRepository authRepository;
    private final RoleRepository roleRepository;
    private final JwtTokenProvider jwtTokenProvider;
    public AuthService(AuthenticationManager authenticationManager, AuthRepository authRepository, RoleRepository roleRepository, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.authRepository = authRepository;
        this.roleRepository = roleRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public LoginTokenData login(LoginRequest request) {
        Authentication authentication = getAuthentication(request.userId(), request.password());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwtToke = jwtTokenProvider.generateToken(authentication);

        return LoginTokenData.builder()
                .accessToken(jwtToke)
                .build();
    }


    public User signUp(SignUpRequest request) {
       if (authRepository.existsByUserId(request.userId())) {
           ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "User ID is already taken");
           throw new BadRequestException(apiResponse);
       }

        List<Role> roles = getUserRoles();

        User newUser = User.builder()
                .userId(request.userId())
                .name(request.name())
                .password(BCrypt.hashpw(request.password(), BCrypt.gensalt()))
                .status(UserStatus.STOP)
                .roles(roles)
                .build();

        return authRepository.save(newUser);

    }


    private Authentication getAuthentication(String email, String password) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

            return authenticationManager.authenticate(authenticationToken);
        } catch (AuthenticationException e) {
            ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "Login Not Match");
            throw new BadRequestException(apiResponse);
        }
    }

    private List<Role> getUserRoles() {
        List<Role> roles = new ArrayList<>();

        roles.add(
                roleRepository.findByRoleName(RoleType.USER)
                        .orElseThrow(() -> new AppException("User Role not Set"))
        );

        return roles;
    }
}
