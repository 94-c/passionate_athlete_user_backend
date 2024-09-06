package com.backend.athlete.domain.user.application;

import com.backend.athlete.support.exception.DuplicatePasswordException;
import com.backend.athlete.domain.branch.domain.Branch;
import com.backend.athlete.domain.branch.domain.BranchRepository;
import com.backend.athlete.domain.user.domain.RoleRepository;
import com.backend.athlete.domain.user.domain.User;
import com.backend.athlete.domain.user.domain.UserRepository;
import com.backend.athlete.domain.user.dto.request.UpdateUserRequest;
import com.backend.athlete.domain.user.dto.response.GetUserResponse;
import com.backend.athlete.domain.user.dto.response.UpdateUserResponse;
import com.backend.athlete.support.exception.NotFoundBranchException;
import com.backend.athlete.domain.auth.jwt.service.CustomUserDetailsImpl;
import com.backend.athlete.support.util.FindUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
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
                .orElseThrow(() -> new NotFoundBranchException(HttpStatus.NOT_FOUND));

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

    protected void checkDuplicatePassword(String password, String passwordCheck) {
        if (!password.equals(passwordCheck)) {
            throw new DuplicatePasswordException(HttpStatus.BAD_REQUEST);
        }
    }

}
