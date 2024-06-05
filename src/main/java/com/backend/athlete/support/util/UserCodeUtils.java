package com.backend.athlete.support.util;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class UserCodeUtils {
    private static final Set<String> generatedCodes = new HashSet<>();

    public static String generateRandomString() {
        String code;
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        code = "G-" + uuid.substring(0, 6);

        while (generatedCodes.contains(code)) {
            uuid = UUID.randomUUID().toString().replaceAll("-", "");
            code = "G-" + uuid.substring(0, 6);
        }

        generatedCodes.add(code);
        return code;
    }

}
