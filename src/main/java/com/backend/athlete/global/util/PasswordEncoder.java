package com.backend.athlete.global.util;


import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {

    private static PasswordEncoder instance;

    // private constructor to prevent instantiation
    private PasswordEncoder() {}

    public static synchronized PasswordEncoder getInstance() {
        if (instance == null) {
            instance = new PasswordEncoder();
        }
        return instance;
    }

    public String encode(String password) {
        return password;
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        return rawPassword.equals(encodedPassword);
    }
}
