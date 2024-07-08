package com.backend.athlete.domain.user.application;

import com.backend.athlete.domain.auth.exception.DuplicatePasswordException;
import com.backend.athlete.domain.branch.Branch;
import com.backend.athlete.domain.branch.BranchRepository;
import com.backend.athlete.domain.user.domain.Role;
import com.backend.athlete.domain.user.domain.RoleRepository;
import com.backend.athlete.domain.user.domain.User;
import com.backend.athlete.domain.user.domain.UserRepository;
import com.backend.athlete.domain.user.dto.request.UpdateUserRequest;
import com.backend.athlete.domain.user.dto.request.UpdateUserRoleRequest;
import com.backend.athlete.domain.user.dto.request.UpdateUserStatusRequest;
import com.backend.athlete.domain.user.dto.response.GetUserResponse;
import com.backend.athlete.domain.user.dto.response.UpdateUserResponse;
import com.backend.athlete.domain.user.dto.response.UpdateUserRoleResponse;
import com.backend.athlete.domain.user.dto.response.UpdateUserStatusResponse;
import com.backend.athlete.support.exception.NotFoundException;
import com.backend.athlete.domain.auth.jwt.service.CustomUserDetailsImpl;
import com.backend.athlete.support.util.FindUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final BranchRepository branchRepository;

    public GetUserResponse getUserInfo(CustomUserDetailsImpl userPrincipal) {
        return GetUserResponse.fromEntity(FindUtils.findByUserId(userPrincipal.getUsername()));
    }

    @Transactional
    public UpdateUserResponse updateUser(CustomUserDetailsImpl userPrincipal, UpdateUserRequest request) {
        User user = FindUtils.findByUserId(userPrincipal.getUsername());
        checkDuplicatePassword(request.getPassword(), request.getPasswordCheck());

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        Branch branch = branchRepository.findByName(request.getBranchName())
                .orElseThrow(() -> new NotFoundException("해당 지점을 찾을 수 없습니다." , HttpStatus.NOT_FOUND));

        user.updateUser(
                encodedPassword,
                request.getGender(),
                request.getWeight(),
                request.getHeight(),
                branch,
                request.getBirthDate(),
                request.getPhoneNumber()
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
                .orElseThrow(() -> new NotFoundException("해당 권한을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        user.updateUserRole(newRole);
        User updatedUser = userRepository.save(user);

        return UpdateUserRoleResponse.fromEntity(updatedUser);
    }

    protected void checkDuplicatePassword(String password, String passwordCheck) {
        if (!password.equals(passwordCheck)) {
            throw new DuplicatePasswordException(HttpStatus.BAD_REQUEST);
        }
    }
}
