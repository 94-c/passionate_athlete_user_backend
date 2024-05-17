package com.backend.athlete.domain.auth.dto.response;

import com.backend.athlete.domain.user.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterUserResponseDto {

    private String userId;
    private String password;
    private String name;
    private String roles;
    @Builder
    public RegisterUserResponseDto(String userId, String password, String name, String roles) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.roles = roles;
    }

    // Entity -> Dto
    public static RegisterUserResponseDto fromEntity(User user) {
        return RegisterUserResponseDto.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .password(user.getPassword())
                .roles(user.getRoles().name())
                .build();
    }

}
