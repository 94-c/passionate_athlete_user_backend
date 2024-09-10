package com.backend.athlete.domain.auth.application;

import com.backend.athlete.domain.auth.domain.AuthRepository;
import com.backend.athlete.domain.physical.domain.Physical;
import com.backend.athlete.domain.physical.domain.PhysicalRepository;
import com.backend.athlete.domain.physical.dto.request.CreatePhysicalRequest;
import com.backend.athlete.support.exception.DuplicatePasswordException;
import com.backend.athlete.support.exception.IsExistUserIddException;
import com.backend.athlete.support.exception.NotFoundBranchException;
import com.backend.athlete.support.exception.NotFoundRoleException;
import com.backend.athlete.domain.branch.domain.Branch;
import com.backend.athlete.domain.branch.domain.BranchRepository;
import com.backend.athlete.domain.user.domain.Role;
import com.backend.athlete.domain.user.domain.RoleRepository;
import com.backend.athlete.domain.user.domain.User;
import com.backend.athlete.domain.user.domain.type.UserRoleType;
import com.backend.athlete.domain.auth.dto.request.LoginRequest;
import com.backend.athlete.domain.auth.dto.request.RegisterRequest;
import com.backend.athlete.domain.auth.dto.response.LoginResponse;
import com.backend.athlete.domain.auth.dto.response.RegisterResponse;
import com.backend.athlete.domain.auth.jwt.JwtTokenProvider;
import com.backend.athlete.domain.auth.jwt.service.CustomUserDetailsImpl;
import com.backend.athlete.support.util.UserCodeUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;
    private final RoleRepository roleRepository;
    private final BranchRepository branchRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final PhysicalRepository physicalRepository;
    private final JwtTokenProvider jwtTokenUtil;

    public RegisterResponse register(RegisterRequest request) {
        isExistUserId(request.getUserId());
        checkDuplicatePassword(request.getPassword(), request.getPasswordCheck());

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        request.setPassword(encodedPassword);
        request.setCode(generateUserCode());

        Branch branch = branchRepository.findByName(request.getBranchName())
                .orElseThrow(() -> new NotFoundBranchException(HttpStatus.NOT_FOUND));

        Set<Role> roles = new HashSet<>();
        roles.add(getUserRoleTypeRole());
        request.setRoleIds(roles);

        User registerUser = authRepository.save(RegisterRequest.toEntity(request, branch));
        Physical initalPhysical = new Physical(registerUser, request.getWeight(), registerUser.getHeight(), 0.0, 0.0, LocalDateTime.now(), 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        physicalRepository.save(initalPhysical);

        return RegisterResponse.fromEntity(registerUser);
    }


    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUserId(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenUtil.generateJwtToken(authentication);

        CustomUserDetailsImpl userDetails = (CustomUserDetailsImpl) authentication.getPrincipal();

        checkEncodePassword(request.getPassword(), userDetails.getPassword());

        return LoginResponse.fromEntity(userDetails, token);
    }

    @Transactional
    public boolean checkUserIdExists(String userId) {
        return authRepository.existsByUserId(userId);
    }

    @Transactional
    public boolean checkUserPhoneExists(String phoneNumber) {
        return authRepository.existsByPhoneNumber(phoneNumber);
    }

    private String generateUserCode() {
        return UserCodeUtils.generateRandomString();
    }


    protected void isExistUserId(String userId) {
        if (authRepository.findByUserId(userId).isPresent()) {
            throw new IsExistUserIddException(HttpStatus.BAD_REQUEST);
        }
    }

    protected void checkDuplicatePassword(String password, String passwordCheck) {
        if (!password.equals(passwordCheck)) {
            throw new DuplicatePasswordException(HttpStatus.BAD_REQUEST);
        }
    }

    protected void checkEncodePassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new DuplicatePasswordException(HttpStatus.BAD_REQUEST);
        }
    }

    protected Role getUserRoleTypeRole() {
        Optional<Role> roleOptional = roleRepository.findByName(UserRoleType.USER);
        return roleOptional.orElseThrow(() -> new NotFoundRoleException(HttpStatus.NOT_FOUND));
    }

}
