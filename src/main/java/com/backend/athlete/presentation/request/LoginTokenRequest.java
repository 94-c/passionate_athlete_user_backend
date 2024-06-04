package com.backend.athlete.presentation.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginTokenRequest {

    private String userId;
    private String password;

}
