package com.backend.athlete.application;

import com.backend.athlete.domain.auth.AuthRepository;
import com.backend.athlete.domain.branch.Branch;
import com.backend.athlete.domain.branch.BranchRepository;
import com.backend.athlete.domain.user.Role;
import com.backend.athlete.domain.user.RoleRepository;
import com.backend.athlete.domain.user.User;
import com.backend.athlete.domain.user.type.UserRoleType;
import com.backend.athlete.presentation.user.request.LoginTokenRequest;
import com.backend.athlete.presentation.user.request.RegisterUserRequest;
import com.backend.athlete.presentation.user.response.LoginTokenResponse;
import com.backend.athlete.presentation.user.response.RegisterUserResponse;
import com.backend.athlete.support.exception.AuthException;
import com.backend.athlete.support.exception.ServiceException;
import com.backend.athlete.support.jwt.JwtTokenProvider;
import com.backend.athlete.support.jwt.service.CustomUserDetailsImpl;
import com.backend.athlete.support.util.UserCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class AuthService {

    private final AuthRepository authRepository;
    private final RoleRepository roleRepository;
    private final BranchRepository branchRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenUtil;

    public AuthService(AuthRepository authRepository, RoleRepository roleRepository, BranchRepository branchRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenUtil) {
        this.authRepository = authRepository;
        this.roleRepository = roleRepository;
        this.branchRepository = branchRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public RegisterUserResponse register(RegisterUserRequest request) {
        isExistUserId(request.getUserId());
        checkDuplicatePassword(request.getPassword(), request.getPasswordCheck());

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        request.setPassword(encodedPassword);
        request.setCode(generateUserCode());

        Branch branch = branchRepository.findByName(request.getBranchName())
                .orElseThrow(() -> new ServiceException("해당 지점을 찾을 수 없습니다."));

        Set<Role> roles = new HashSet<>();
        roles.add(getUserRoleTypeRole());
        request.setRoleIds(roles);

        User registerUser = authRepository.save(RegisterUserRequest.toEntity(request, branch));

        return RegisterUserResponse.fromEntity(registerUser);
    }


    public LoginTokenResponse login(LoginTokenRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUserId(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenUtil.generateJwtToken(authentication);

        CustomUserDetailsImpl userDetails = (CustomUserDetailsImpl) authentication.getPrincipal();

        checkEncodePassword(request.getPassword(), userDetails.getPassword());

        return LoginTokenResponse.fromEntity(userDetails, token);
    }

    public boolean checkUserIdExists(String userId) {
        return authRepository.existsByUserId(userId);
    }

    private String generateUserCode() {
        return UserCodeUtils.generateRandomString();
    }


    protected void isExistUserId(String userId) {
        if (authRepository.findByUserId(userId).isPresent()) {
            throw new AuthException("이미 사용 중인 아이디 입니다.");
        }
    }

    protected void checkDuplicatePassword(String password, String passwordCheck) {
        if (!password.equals(passwordCheck)) {
            throw new AuthException("패스워드가 불일치 합니다.");
        }
    }

    protected void checkEncodePassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new AuthException("패스워드가 불일치 합니다.");
        }
    }

    /**
     * 회원 권한
     */
    protected Role getUserRoleTypeRole() {
        Optional<Role> roleOptional = roleRepository.findByName(UserRoleType.USER);
        return roleOptional.orElseThrow(() -> new AuthException("해당 권한이 존재 하지 않습니다."));
    }

}
