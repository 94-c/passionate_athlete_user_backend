package com.backend.athlete.domain.auth.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@NoArgsConstructor
public class LoginTokenResponseDto {
    private String userId;
    private String token;

    @Builder
    public LoginTokenResponseDto(String userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    // Entity -> Dto
    public static LoginTokenResponseDto fromEntity(UserDetails user, String token) {
        return LoginTokenResponseDto.builder()
                .userId(user.getUsername())
                .token(token)
                .build();
    }
}
