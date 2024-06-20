package com.backend.athlete.presentation.user.response;

import com.backend.athlete.domain.user.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class RegisterUserResponse {

    private String code;
    private String userId;
    private String name;
    private String gender;
    private Double weight;
    private Double height;
    private String branchName;
    private String birthDate;
    private String phoneNumber;
    private Set<String> roles;

    public RegisterUserResponse(String code, String userId, String name, String gender, Double weight, Double height, String branchName, String birthDate, String phoneNumber,  Set<String> roles) {
        this.code = code;
        this.userId = userId;
        this.name = name;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.branchName = branchName;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.roles = roles;
    }

    public static RegisterUserResponse fromEntity(User user) {
        Set<String> roleNames = user.getRoles().stream()
                .map(role -> role.getName().toString())
                .collect(Collectors.toSet());

        return new RegisterUserResponse(
                user.getCode(),
                user.getUserId(),
                user.getName(),
                user.getGender().toString(),
                user.getWeight(),
                user.getHeight(),
                user.getBranch().getName(),
                user.getBirthDate(),
                user.getPhoneNumber(),
                roleNames
        );
    }
}
