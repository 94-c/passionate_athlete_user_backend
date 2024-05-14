package com.backend.athlete.domain.auth.dto.request;

import com.backend.athlete.domain.auth.model.Role;
import com.backend.athlete.domain.auth.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterUserRequestDto {

    private String userId;
    private String password;
    private String passwordCheck;
    private String name;

    @Builder
    public RegisterUserRequestDto(String userId, String password, String passwordCheck, String name) {
        this.userId = userId;
        this.password = password;
        this.passwordCheck = passwordCheck;
        this.name = name;
    }

    // DTO -> Entity
    public static User ofEntity(RegisterUserRequestDto dto) {
        return User.builder()
                .userId(dto.getUserId())
                .password(dto.getPassword())
                .name(dto.getName())
                .roles(Role.USER)
                .build();
    }

}

