package com.backend.athlete.domain.auth.service;

import com.backend.athlete.domain.auth.dto.request.RegisterUserRequestDto;
import com.backend.athlete.domain.auth.dto.response.RegisterUserResponseDto;
import com.backend.athlete.domain.auth.model.User;
import com.backend.athlete.domain.auth.repository.AuthRepository;
import com.backend.athlete.global.exception.AuthException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final AuthRepository authRepository;

    public HttpStatus checkUserIdDuplicate(String userId) {
        isExistUserId(userId);
        return HttpStatus.OK;
    }

    public RegisterUserResponseDto register(RegisterUserRequestDto dto) {
        isExistUserId(dto.getUserId());
        checkDuplicatePassword(dto.getPassword(), dto.getPasswordCheck());

        User registerUser = authRepository.save(
                RegisterUserRequestDto.ofEntity(dto));

        return RegisterUserResponseDto.fromEntity(registerUser);
    }


    /**
     * 아이디 중복 체크 여부
     */
    protected void isExistUserId(String userId) {
        if (authRepository.findByUserId(userId).isPresent()) {
            throw new AuthException("이미 사용 중인 아이디 입니다." , HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 비밀번호와 비밀번호 확인이 같은지 체크
     */
    protected void checkDuplicatePassword(String password, String passwordCheck) {
        if (!password.equals(passwordCheck)) {
            throw new AuthException("패스워드가 불일치 합니다.", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 사용자가 입력한 비번과 DB에 저장된 비번이 같은지 체크 : 인코딩 확인
     */
    protected void checkEncodePassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new AuthException("패스워드 불일치", HttpStatus.BAD_REQUEST);
        }
    }

}
