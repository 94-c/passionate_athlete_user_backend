package com.backend.athlete.user.auth.application;

import com.backend.athlete.user.auth.domain.AuthRepository;
import com.backend.athlete.user.auth.dto.request.*;
import com.backend.athlete.user.auth.dto.response.FindUserIdResponse;
import com.backend.athlete.user.physical.domain.Physical;
import com.backend.athlete.user.physical.domain.PhysicalRepository;
import com.backend.athlete.support.exception.*;
import com.backend.athlete.user.branch.domain.Branch;
import com.backend.athlete.user.branch.domain.BranchRepository;
import com.backend.athlete.user.user.domain.Role;
import com.backend.athlete.user.user.domain.RoleRepository;
import com.backend.athlete.user.user.domain.User;
import com.backend.athlete.user.user.domain.type.UserRoleType;
import com.backend.athlete.user.auth.dto.response.LoginResponse;
import com.backend.athlete.user.auth.dto.response.RegisterResponse;
import com.backend.athlete.user.auth.jwt.JwtTokenProvider;
import com.backend.athlete.user.auth.jwt.service.CustomUserDetailsImpl;
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

    public boolean verifyUser(VerifyUserRequest request) {
        return authRepository.findByUserId(request.getUserId())
                .filter(user -> user.getName().equals(request.getName()))
                .filter(user -> user.getPhoneNumber().equals(request.getPhoneNumber()))
                .isPresent();
    }

    public boolean resetPassword(ResetPasswordRequest request) {
        return authRepository.findByUserId(request.getUserId())
                .map(user -> {
                    user.setPassword(passwordEncoder.encode(request.getNewPassword()));
                    authRepository.save(user);
                    return true;
                })
                .orElse(false);
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


    public FindUserIdResponse findUserId(FindUserIdRequest request) {
        return authRepository.findByNameAndPhoneNumberAndBirthDate(
                        request.getName(),
                        request.getPhoneNumber(),
                        request.getBirthDate()
                ).map(FindUserIdResponse::fromEntity)
                .orElseThrow(() -> new NotFoundException("입력하신 정보와 일치하는 아이디가 없습니다.", HttpStatus.NOT_FOUND));
    }

}
