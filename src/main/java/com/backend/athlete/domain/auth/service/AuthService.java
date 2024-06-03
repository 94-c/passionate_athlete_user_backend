package com.backend.athlete.domain.auth.service;

import com.backend.athlete.domain.auth.dto.request.LoginTokenRequest;
import com.backend.athlete.domain.auth.dto.request.RegisterUserRequest;
import com.backend.athlete.domain.auth.dto.response.LoginTokenResponse;
import com.backend.athlete.domain.auth.dto.response.RegisterUserResponse;
import com.backend.athlete.domain.branch.model.Branch;
import com.backend.athlete.domain.branch.repository.BranchRepository;
import com.backend.athlete.domain.user.model.Role;
import com.backend.athlete.domain.user.model.User;
import com.backend.athlete.domain.auth.repository.AuthRepository;
import com.backend.athlete.domain.user.model.type.UserRoleType;
import com.backend.athlete.domain.user.repository.RoleRepository;
import com.backend.athlete.global.exception.AuthException;
import com.backend.athlete.global.exception.ServiceException;
import com.backend.athlete.global.jwt.JwtTokenProvider;
import com.backend.athlete.global.jwt.service.CustomUserDetailsImpl;
import com.backend.athlete.global.util.UserCodeUtils;
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

    public RegisterUserResponse register(RegisterUserRequest dto) {
        isExistUserId(dto.getUserId());
        checkDuplicatePassword(dto.getPassword(), dto.getPasswordCheck());

        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        dto.setPassword(encodedPassword);
        dto.setCode(generateUserCode());

        Branch branch = branchRepository.findByName(dto.getBranchName())
                .orElseThrow(() -> new ServiceException("해당 지점을 찾을 수 없습니다."));

        Set<Role> roles = new HashSet<>();
        roles.add(getUserRoleTypeRole());
        dto.setRoleIds(roles);

        User registerUser = authRepository.save(RegisterUserRequest.toEntity(dto, branch));

        return RegisterUserResponse.fromEntity(registerUser);
    }


    public LoginTokenResponse login(LoginTokenRequest dto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUserId(), dto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenUtil.generateJwtToken(authentication);

        CustomUserDetailsImpl userDetails = (CustomUserDetailsImpl) authentication.getPrincipal();

        checkEncodePassword(dto.getPassword(), userDetails.getPassword());

        return LoginTokenResponse.fromEntity(userDetails, token);
    }


    /**
     * 회원 코드 자동 입력
     */
    private String generateUserCode() {
        return UserCodeUtils.generateRandomString();
    }

    /**
     * 아이디 중복 체크 여부
     */
    protected void isExistUserId(String userId) {
        if (authRepository.findByUserId(userId).isPresent()) {
            throw new AuthException("이미 사용 중인 아이디 입니다.");
        }
    }

    /**
     * 비밀번호와 비밀번호 확인이 같은지 체크
     */
    protected void checkDuplicatePassword(String password, String passwordCheck) {
        if (!password.equals(passwordCheck)) {
            throw new AuthException("패스워드가 불일치 합니다.");
        }
    }

    /**
     * 사용자가 입력한 비번과 DB에 저장된 비번이 같은지 체크 : 인코딩 확인
     */
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
