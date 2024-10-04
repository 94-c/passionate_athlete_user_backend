package com.backend.athlete.user.auth.data;

import lombok.Getter;

import java.util.Set;

@Getter
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
