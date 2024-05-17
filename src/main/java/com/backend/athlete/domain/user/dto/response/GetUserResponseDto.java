package com.backend.athlete.domain.user.dto.response;

import com.backend.athlete.domain.user.model.User;
import com.backend.athlete.domain.user.model.type.UserGenderType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class GetUserResponseDto {

    private String code;
    private String userId;
    private String name;
    private String gender;
    private Double weight;
    private Double height;
    private Set<String> roles;
    private String createdDate;

    public GetUserResponseDto(String code, String userId, String name, String gender, Double weight, Double height, Set<String> roles, String createdDate) {
        this.code = code;
        this.userId = userId;
        this.name = name;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.roles = roles;
        this.createdDate = createdDate;
    }

    public static GetUserResponseDto fromEntity(User user) {
        Set<String> roleNames = user.getRoles().stream()
                .map(role -> role.getName().toString())
                .collect(Collectors.toSet());

        return new GetUserResponseDto(
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
