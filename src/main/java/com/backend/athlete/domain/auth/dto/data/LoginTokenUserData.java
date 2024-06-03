package com.backend.athlete.domain.auth.dto.data;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter @Setter
public class LoginTokenUserData {
    private String code;
    private String userId;
    private String name;
    private Set<String> roles;

    public LoginTokenUserData(String code, String userId, String name, Set<String> roles) {
        this.code = code;
        this.userId = userId;
        this.name = name;
        this.roles = roles;
    }
}
