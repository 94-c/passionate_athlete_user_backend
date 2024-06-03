package com.backend.athlete.domain.user.dto.request;

import com.backend.athlete.domain.user.model.type.UserRoleType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateUserRoleRequest {

    @NotNull(message = "자격을 입력 해주세요.")
    private UserRoleType role;

}
