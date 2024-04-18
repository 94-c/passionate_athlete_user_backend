package com.backend.athlete.domain.auth.dto;

import com.backend.athlete.domain.auth.dto.data.LoginTokenData;
import io.swagger.v3.oas.annotations.media.Schema;

public record LoginResponse(
        LoginTokenData tokenData
) {
}

