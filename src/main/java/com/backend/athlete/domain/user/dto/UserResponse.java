package com.backend.athlete.domain.user.dto;


import com.backend.athlete.global.jwt.UserPrincipal;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public record UserResponse(
        Long id,
        String userId,
        String name,
        Collection<? extends GrantedAuthority> authorities
) {
    public static UserResponse fromUserPrincipal(UserPrincipal userPrincipal) {
        return new UserResponse(
                userPrincipal.getId(),
                userPrincipal.getUserId(),
                userPrincipal.getUsername(),
                userPrincipal.getAuthorities()
        );
    }

}
