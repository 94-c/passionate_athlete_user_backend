package com.backend.athlete.domain.user.service;

import com.backend.athlete.domain.user.dto.request.UpdateUserRequestDto;
import com.backend.athlete.domain.user.dto.request.UpdateUserStatusRequest;
import com.backend.athlete.domain.user.dto.response.GetUserResponseDto;
import com.backend.athlete.domain.user.dto.response.UpdateUserResponseDto;
import com.backend.athlete.domain.user.dto.response.UpdateUserStatusResponse;
import com.backend.athlete.domain.user.model.User;
import com.backend.athlete.domain.user.repository.UserRepository;
import com.backend.athlete.global.exception.AuthException;
import com.backend.athlete.global.exception.ServiceException;
import com.backend.athlete.global.jwt.service.CustomUserDetailsImpl;
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

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public GetUserResponseDto getUserInfo(CustomUserDetailsImpl userPrincipal) {
        User findUser = userRepository.findByUserId(userPrincipal.getUsername());
        if (findUser == null) {
            throw new UsernameNotFoundException("회원이 존재 하지 않습니다.");
        }
        return GetUserResponseDto.fromEntity(findUser);
    }

    @Transactional
    public UpdateUserResponseDto updateUser(CustomUserDetailsImpl userPrincipal, UpdateUserRequestDto dto) {
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

        return UpdateUserResponseDto.fromEntity(findUser);
    }

    protected void checkDuplicatePassword(String password, String passwordCheck) {
        if (!password.equals(passwordCheck)) {
            throw new AuthException("패스워드가 불일치 합니다.");
        }
    }

    public UpdateUserStatusResponse updateUserStatus(Long userId, UpdateUserStatusRequest request, CustomUserDetailsImpl userPrincipal) {
        User user = userRepository.findByUserId(userPrincipal.getUsername());

        User updatedUser = userRepository.save(UpdateUserStatusRequest.toEntity(request, user));

        return new UpdateUserStatusResponse(updatedUser.getId(), updatedUser.getStatus(), "User status updated successfully");
    }
}
