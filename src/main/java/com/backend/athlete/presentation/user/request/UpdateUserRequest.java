package com.backend.athlete.presentation.user.request;

import com.backend.athlete.domain.user.type.UserGenderType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateUserRequest {

    @NotBlank(message = "패스워드를 입력하세요.")
    @Size(min = 8, message = "패스워드는 최소 8자 이상이어야 합니다.")
    private String password;

    @NotBlank(message = "패스워드를 한번 더 입력하세요.")
    @Size(min = 8, message = "패스워드는 최소 8자 이상이어야 합니다.")
    private String passwordCheck;

    @NotNull(message = "성별을 선택하세요.")
    private UserGenderType gender;

    @NotNull(message = "체중을 입력하세요.")
    private Double weight;

    @NotNull(message = "키를 입력하세요.")
    private Double height;

    protected UpdateUserRequest() {}

}

