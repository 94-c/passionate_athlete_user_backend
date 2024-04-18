package com.backend.athlete.domain.auth.dto.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "인증 토큰 정보")
public class LoginTokenData {

    @Schema(description = "토큰 타입 - Bearer(JWT), Access(비밀번호 재설정)")
    private String tokenType = "Bearer";

    @Schema(description = "토큰 Value")
    private final String accessToken;


    @Builder
    public LoginTokenData(String accessToken) {
        this.accessToken = accessToken;
    }

    public LoginTokenData(String tokenType, String accessToken) {
        this.tokenType = tokenType;
        this.accessToken = accessToken;
    }

}
