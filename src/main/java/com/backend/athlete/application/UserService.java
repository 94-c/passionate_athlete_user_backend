package com.backend.athlete.application;

import com.backend.athlete.domain.user.Role;
import com.backend.athlete.domain.user.RoleRepository;
import com.backend.athlete.domain.user.User;
import com.backend.athlete.domain.user.UserRepository;
import com.backend.athlete.presentation.request.UpdateUserRequest;
import com.backend.athlete.presentation.request.UpdateUserRoleRequest;
import com.backend.athlete.presentation.request.UpdateUserStatusRequest;
import com.backend.athlete.presentation.response.GetUserResponse;
import com.backend.athlete.presentation.response.UpdateUserResponse;
import com.backend.athlete.presentation.response.UpdateUserRoleResponse;
import com.backend.athlete.presentation.response.UpdateUserStatusResponse;
import com.backend.athlete.support.exception.AuthException;
import com.backend.athlete.support.exception.ServiceException;
import com.backend.athlete.support.jwt.service.CustomUserDetailsImpl;
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
        User findUser = userRepository.findByUserId(userPrincipal.getUsername());
        if (findUser == null) {
            throw new UsernameNotFoundException("회원이 존재 하지 않습니다.");
        }
        return GetUserResponse.fromEntity(findUser);
    }

    @Transactional
    public UpdateUserResponse updateUser(CustomUserDetailsImpl userPrincipal, UpdateUserRequest dto) {
        User findUser = userRepository.findByUserId(userPrincipal.getUsername());
        if (findUser == null) {
            throw new UsernameNotFoundException("회원이 존재 하지 않습니다.");
        }
        checkDuplicatePassword(dto.getPassword(), dto.getPasswordCheck());

        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        findUser.updateUser(
                encodedPassword,
                dto.getGender(),
                dto.getWeight(),
                dto.getHeight()
        );

        userRepository.save(findUser);

        return UpdateUserResponse.fromEntity(findUser);
    }

    protected void checkDuplicatePassword(String password, String passwordCheck) {
        if (!password.equals(passwordCheck)) {
            throw new AuthException("패스워드가 불일치 합니다.");
        }
    }

    public UpdateUserStatusResponse updateUserStatus(Long userId, UpdateUserStatusRequest request) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new ServiceException("해당 회원을 찾을 수 없습니다."));
        findUser.updateUserStatus(
            request.getStatus()
        );
        userRepository.save(findUser);
        return UpdateUserStatusResponse.fromEntity(findUser);
    }

    public UpdateUserRoleResponse updateUserRole(Long userId, UpdateUserRoleRequest request) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new ServiceException("해당 회원을 찾을 수 없습니다."));

        Role newRole = roleRepository.findByName(request.getRole())
                .orElseThrow(() -> new ServiceException("해당 권한을 찾을 수 없습니다."));

        findUser.updateUserRole(newRole);
        User updatedUser = userRepository.save(findUser);

        return UpdateUserRoleResponse.fromEntity(updatedUser);
    }
}
