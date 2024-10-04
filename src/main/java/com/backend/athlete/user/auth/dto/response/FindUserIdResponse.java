package com.backend.athlete.user.auth.dto.response;

import com.backend.athlete.user.user.domain.User;
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
