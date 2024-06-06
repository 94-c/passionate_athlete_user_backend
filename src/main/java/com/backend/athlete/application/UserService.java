package com.backend.athlete.application;

import com.backend.athlete.domain.user.Role;
import com.backend.athlete.domain.user.RoleRepository;
import com.backend.athlete.domain.user.User;
import com.backend.athlete.domain.user.UserRepository;
import com.backend.athlete.presentation.user.request.UpdateUserRequest;
import com.backend.athlete.presentation.user.request.UpdateUserRoleRequest;
import com.backend.athlete.presentation.user.request.UpdateUserStatusRequest;
import com.backend.athlete.presentation.user.response.GetUserResponse;
import com.backend.athlete.presentation.user.response.UpdateUserResponse;
import com.backend.athlete.presentation.user.response.UpdateUserRoleResponse;
import com.backend.athlete.presentation.user.response.UpdateUserStatusResponse;
import com.backend.athlete.support.exception.AuthException;
import com.backend.athlete.support.exception.ServiceException;
import com.backend.athlete.support.jwt.service.CustomUserDetailsImpl;
import com.backend.athlete.support.util.FindUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public GetUserResponse getUserInfo(CustomUserDetailsImpl userPrincipal) {
        return GetUserResponse.fromEntity(FindUtils.findByUserId(userPrincipal.getUsername()));
    }

    @Transactional
    public UpdateUserResponse updateUser(CustomUserDetailsImpl userPrincipal, UpdateUserRequest request) {
        User user = FindUtils.findByUserId(userPrincipal.getUsername());
        checkDuplicatePassword(request.getPassword(), request.getPasswordCheck());

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        user.updateUser(
                encodedPassword,
                request.getGender(),
                request.getWeight(),
                request.getHeight()
        );

        userRepository.save(user);

        return UpdateUserResponse.fromEntity(user);
    }

    public UpdateUserStatusResponse updateUserStatus(Long userId, UpdateUserStatusRequest request) {
        User user = FindUtils.findById(userId);
        user.updateUserStatus(
            request.getStatus()
        );
        userRepository.save(user);
        return UpdateUserStatusResponse.fromEntity(user);
    }

    public UpdateUserRoleResponse updateUserRole(Long userId, UpdateUserRoleRequest request) {
        User user = FindUtils.findById(userId);

        Role newRole = roleRepository.findByName(request.getRole())
                .orElseThrow(() -> new ServiceException("해당 권한을 찾을 수 없습니다."));

        user.updateUserRole(newRole);
        User updatedUser = userRepository.save(user);

        return UpdateUserRoleResponse.fromEntity(updatedUser);
    }

    protected void checkDuplicatePassword(String password, String passwordCheck) {
        if (!password.equals(passwordCheck)) {
            throw new AuthException("패스워드가 불일치 합니다.");
        }
    }
}
