package com.backend.athlete.domain.auth.dto.response;

import com.backend.athlete.domain.auth.data.LoginTokenUserData;
import com.backend.athlete.domain.auth.jwt.service.CustomUserDetailsImpl;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class LoginResponse {

    private String type = "Bearer";
    private String token;
    private LoginTokenUserData users;

    public LoginResponse(String type, String token, LoginTokenUserData users) {
        this.type = type;
        this.token = token;
        this.users = users;
    }

    // Entity -> Dto
    public static LoginResponse fromEntity(CustomUserDetailsImpl userDetails, String token) {
        Set<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        LoginTokenUserData userDto = new LoginTokenUserData(
                userDetails.getCode(),
                userDetails.getUsername(),
                userDetails.getName(),
                roles
        );

        return new LoginResponse(
                "Bearer",
                token,
                userDto
        );
    }

}
