package com.backend.athlete.global.jwt;

import com.backend.athlete.domain.auth.repository.AuthRepository;
import com.backend.athlete.global.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private AuthRepository authRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.authRepository.findByUserId(username).orElseThrow(
                () -> new ResourceNotFoundException("USER", "User ID : " , username));
    }
}
