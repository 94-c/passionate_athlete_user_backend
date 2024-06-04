package com.backend.athlete.support.jwt.service;

import com.backend.athlete.domain.auth.AuthRepository;
import com.backend.athlete.domain.user.User;
import com.backend.athlete.domain.user.type.UserStatusType;
import com.backend.athlete.support.exception.ServiceException;
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
                () -> new ServiceException("Not Found User Id :: " + username));

        if (user.getStatus() == UserStatusType.WAIT) {
            throw new ServiceException("회원이 활성화되지 않았습니다.");
        }

        return CustomUserDetailsImpl.build(user);
    }
}
