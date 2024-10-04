package com.backend.athlete.user.user.dto.response;

import com.backend.athlete.user.user.domain.User;
import lombok.Getter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class UpdateUserResponse {

    private String code;
    private String userId;
    private String name;
    private String gender;
    private Double weight;
    private Double height;
    private Set<String> roles;
    private String modifiedDate;

    public UpdateUserResponse(String code, String userId, String name, String gender, Double weight, Double height, Set<String> roles, String modifiedDate) {
        this.code = code;
        this.userId = userId;
        this.name = name;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.roles = roles;
        this.modifiedDate = modifiedDate;
    }

    public static UpdateUserResponse fromEntity(User user) {
        Set<String> roleNames = user.getRoles().stream()
                .map(role -> role.getName().toString())
                .collect(Collectors.toSet());

        return new UpdateUserResponse(
                user.getCode(),
                user.getUserId(),
                user.getName(),
                user.getGender().toString(),
                user.getWeight(),
                user.getHeight(),
                roleNames,
                user.getModifiedDate()
        );
    }

}
