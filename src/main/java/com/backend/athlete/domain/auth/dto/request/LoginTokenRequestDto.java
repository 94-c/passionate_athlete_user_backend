package com.backend.athlete.domain.auth.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginTokenRequestDto {

    private String userId;
    private String password;

}
