package com.backend.athlete.user.auth.jwt.service;

import com.backend.athlete.user.auth.domain.AuthRepository;
import com.backend.athlete.user.user.domain.User;
import com.backend.athlete.user.user.domain.type.UserStatusType;
import com.backend.athlete.support.exception.NotForbiddenException;
import com.backend.athlete.support.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final AuthRepository authRepository;

    public CustomUserDetailService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Override
    @Transactional
    public CustomUserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = authRepository.findByUserId(username).orElseThrow(
                () -> new NotFoundException("중복되는 회원이 있습니다." , HttpStatus.NOT_FOUND));

        if (user.getStatus() == UserStatusType.WAIT) {
            throw new NotForbiddenException("회원이 활성화 되지 않았습니다." , HttpStatus.FORBIDDEN);
        }

        return CustomUserDetailsImpl.build(user);
    }
}
