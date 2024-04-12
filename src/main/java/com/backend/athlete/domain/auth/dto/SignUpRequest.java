package com.backend.athlete.domain.auth.dto;

import com.backend.athlete.domain.user.domain.User;
import jakarta.validation.constraints.NotBlank;

public record SignUpRequest(
        @NotBlank String userId,
        @NotBlank String password,
        @NotBlank String name
) {

}
