package com.backend.athlete.domain.user.service;

import com.backend.athlete.domain.user.dto.request.UpdateUserRequestDto;
import com.backend.athlete.domain.user.dto.response.GetUserResponseDto;
import com.backend.athlete.domain.user.dto.response.UpdateUserResponseDto;
import com.backend.athlete.domain.user.model.User;
import com.backend.athlete.domain.user.repository.UserRepository;
import com.backend.athlete.global.exception.AuthException;
import com.backend.athlete.global.exception.ResourceNotFoundException;
import com.backend.athlete.global.jwt.service.CustomUserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
            throw new UsernameNotFoundException("User not found");
        }
        return GetUserResponseDto.fromEntity(findUser);
    }

    public UpdateUserResponseDto updateUser(CustomUserDetailsImpl userPrincipal, UpdateUserRequestDto dto) {
        User findUser = userRepository.findByUserId(userPrincipal.getUsername());
        if (findUser == null) {
            throw new UsernameNotFoundException("User not found");
        }
        checkDuplicatePassword(dto.getPassword(), dto.getPasswordCheck());

        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        findUser.updateUser(
                encodedPassword,
                dto.getGender(),
                dto.getWeight(),
                dto.getHeight()
        );
        return UpdateUserResponseDto.fromEntity(findUser);
    }

    protected void checkDuplicatePassword(String password, String passwordCheck) {
        if (!password.equals(passwordCheck)) {
            throw new AuthException("패스워드가 불일치 합니다.", HttpStatus.BAD_REQUEST);
        }
    }
}
