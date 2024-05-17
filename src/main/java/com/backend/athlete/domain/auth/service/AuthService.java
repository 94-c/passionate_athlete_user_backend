package com.backend.athlete.domain.auth.service;

import com.backend.athlete.domain.auth.dto.request.LoginTokenRequestDto;
import com.backend.athlete.domain.auth.dto.request.RegisterUserRequestDto;
import com.backend.athlete.domain.auth.dto.response.LoginTokenResponseDto;
import com.backend.athlete.domain.auth.dto.response.RegisterUserResponseDto;
import com.backend.athlete.domain.user.model.User;
import com.backend.athlete.domain.auth.repository.AuthRepository;
import com.backend.athlete.global.exception.AuthException;
import com.backend.athlete.global.jwt.CustomUserDetailService;
import com.backend.athlete.global.jwt.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final AuthRepository authRepository;

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailService customUserDetailService;
    private final JwtTokenUtil jwtTokenUtil;

    public HttpStatus checkUserIdDuplicate(String userId) {
        isExistUserId(userId);
        return HttpStatus.OK;
    }

    public RegisterUserResponseDto register(RegisterUserRequestDto dto) {
        isExistUserId(dto.getUserId());
        checkDuplicatePassword(dto.getPassword(), dto.getPasswordCheck());

        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        dto.setPassword(encodedPassword);

        User registerUser = authRepository.save(
                RegisterUserRequestDto.ofEntity(dto));

        return RegisterUserResponseDto.fromEntity(registerUser);
    }

    public LoginTokenResponseDto login(LoginTokenRequestDto dto) {
        authenticate(dto.getUserId(), dto.getPassword());
        UserDetails userDetails = customUserDetailService.loadUserByUsername(dto.getUserId());
        checkEncodePassword(dto.getPassword(), userDetails.getPassword());
        String token = jwtTokenUtil.generateToken(userDetails);
        return LoginTokenResponseDto.fromEntity(userDetails, token);
    }


    /**
     * 사용자 인증 여부
     */
    protected void authenticate(String userId, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userId, password));
        } catch (DisabledException e) {
            throw new AuthException("인증되지 않은 아이디입니다.", HttpStatus.BAD_REQUEST);
        } catch (BadCredentialsException e) {
            throw new AuthException("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
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
