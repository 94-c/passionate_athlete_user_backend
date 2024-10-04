package com.backend.athlete.user.admin.dto.user;

import com.backend.athlete.user.user.domain.type.UserStatusType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateUserStatusRequest {

    @NotNull(message = "회원 상태를 입력 해주세요.")
    private UserStatusType status;

}
