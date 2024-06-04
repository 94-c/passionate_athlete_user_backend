package com.backend.athlete.presentation.user.request;

import com.backend.athlete.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
public class LoginTokenRequest {

    private String userId;
    private String password;

}
