package com.backend.athlete.global.jwt.service;

import com.backend.athlete.domain.auth.repository.AuthRepository;
import com.backend.athlete.domain.user.model.User;
import com.backend.athlete.global.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = authRepository.findByUserId(username).orElseThrow(
                () -> new ResourceNotFoundException("USER", "User ID : " , username));
        return CustomUserDetailsImpl.build(user);
    }
}
