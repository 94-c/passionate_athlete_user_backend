package com.backend.athlete.domain.auth.dto;

import com.backend.athlete.domain.user.domain.User;

public record SignUpResponse(
        User newUser
) {
}
