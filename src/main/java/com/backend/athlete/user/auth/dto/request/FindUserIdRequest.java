package com.backend.athlete.user.auth.dto.request;

import com.backend.athlete.user.user.domain.User;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FindUserIdRequest {
    private String name;
    private String phoneNumber;
    private String birthDate;

    public FindUserIdRequest(String name, String phoneNumber, String birthDate) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
    }

    public static User fromEntity(FindUserIdRequest request) {
        return new User(
                request.getName(),
                request.getPhoneNumber(),
                request.getBirthDate()
        );
    }
}
