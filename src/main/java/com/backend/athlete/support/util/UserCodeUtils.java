package com.backend.athlete.support.util;

import java.util.UUID;

public class UserCodeUtils {
    public static String generateRandomString() {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return "G-" + uuid.substring(0, 6);
    }

}
