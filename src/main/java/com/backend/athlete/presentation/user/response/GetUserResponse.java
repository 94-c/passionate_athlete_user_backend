package com.backend.athlete.presentation.user.response;

import com.backend.athlete.domain.user.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class GetUserResponse {

    private String code;
    private String userId;
    private String name;
    private String gender;
    private Double weight;
    private Double height;
    private Set<String> roles;
    private String createdDate;

    public GetUserResponse(String code, String userId, String name, String gender, Double weight, Double height, Set<String> roles, String createdDate) {
        this.code = code;
        this.userId = userId;
        this.name = name;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.roles = roles;
        this.createdDate = createdDate;
    }

    public static GetUserResponse fromEntity(User user) {
        Set<String> roleNames = user.getRoles().stream()
                .map(role -> role.getName().toString())
                .collect(Collectors.toSet());

        return new GetUserResponse(
                user.getCode(),
                user.getUserId(),
                user.getName(),
                user.getGender().toString(),
                user.getWeight(),
                user.getHeight(),
                roleNames,
                user.getCreatedDate()
        );
    }

}
