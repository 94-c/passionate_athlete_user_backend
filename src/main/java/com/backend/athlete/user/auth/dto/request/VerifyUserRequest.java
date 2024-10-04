package com.backend.athlete.user.auth.dto.request;

import com.backend.athlete.user.user.domain.User;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class VerifyUserRequest {
    private String userId;
    private String name;
    private String phoneNumber;

    public VerifyUserRequest(String userId, String name, String phoneNumber) {
        this.userId = userId;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public static User toEntity(VerifyUserRequest request) {
        return new User(
                request.getUserId(),
                request.getName(),
                request.getPhoneNumber()
        );
    }
}
