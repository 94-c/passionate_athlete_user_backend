package com.backend.athlete.global.util;


import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {

    private static final PasswordEncoder passwordEncoder = new PasswordEncoder();

    public static String encode(String password) {
        return passwordEncoder.encode(password);
    }

    public static boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

}
