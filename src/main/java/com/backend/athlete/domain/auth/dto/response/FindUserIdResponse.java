package com.backend.athlete.domain.auth.dto.response;

import com.backend.athlete.domain.user.domain.User;
import lombok.Getter;

@Getter
public class FindUserIdResponse {

    private String userId;

    public FindUserIdResponse(String userId) {
        this.userId = userId;
    }

    public static FindUserIdResponse fromEntity(User user) {
        return new FindUserIdResponse(user.getUserId());
    }

}
