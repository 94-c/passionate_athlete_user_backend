package com.backend.athlete.domain.auth.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResetPasswordRequest {
    private String userId;
    private String newPassword;
}
