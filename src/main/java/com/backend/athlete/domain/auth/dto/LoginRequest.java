package com.backend.athlete.domain.auth.dto;

public record LoginRequest(
        String userId,
        String password
) {
}
