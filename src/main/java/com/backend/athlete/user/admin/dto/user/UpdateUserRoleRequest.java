package com.backend.athlete.user.admin.dto.user;

import com.backend.athlete.user.user.domain.type.UserRoleType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateUserRoleRequest {

    @NotNull(message = "자격을 입력 해주세요.")
    private UserRoleType role;

}
