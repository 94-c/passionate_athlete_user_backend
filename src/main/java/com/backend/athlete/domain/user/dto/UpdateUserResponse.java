package com.backend.athlete.domain.user.dto;

import com.backend.athlete.domain.user.domain.User;
import com.backend.athlete.domain.user.dto.data.UpdateUserData;

public record UpdateUserResponse(
        UpdateUserData updateUser
) {

}
