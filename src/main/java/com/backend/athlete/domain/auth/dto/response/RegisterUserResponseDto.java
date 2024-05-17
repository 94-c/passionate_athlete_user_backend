package com.backend.athlete.domain.auth.dto.response;

import com.backend.athlete.domain.user.model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class RegisterUserResponseDto {

    private String code;
    private String userId;
    private String name;
    private String gender;
    private Double weight;
    private Double height;
    private Set<String> roles;

    public RegisterUserResponseDto(String code, String userId, String name, String gender, Double weight, Double height, Set<String> roles) {
        this.code = code;
        this.userId = userId;
        this.name = name;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.roles = roles;
    }

    // Entity -> Dto
    public static RegisterUserResponseDto fromEntity(User user) {
        Set<String> roleNames = user.getRoles().stream()
                .map(role -> role.getName().toString())
                .collect(Collectors.toSet());

        return new RegisterUserResponseDto(
                user.getCode(),
                user.getUserId(),
                user.getName(),
                user.getGender().toString(),
                user.getWeight(),
                user.getHeight(),
                roleNames
        );
    }
}
