package com.backend.athlete.domain.user.dto;


public record UserResponse(
        Long id,
        String userId,
        String userName

) {
}
