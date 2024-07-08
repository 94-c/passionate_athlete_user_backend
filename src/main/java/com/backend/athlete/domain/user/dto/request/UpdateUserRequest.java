package com.backend.athlete.domain.user.dto.request;

import com.backend.athlete.domain.user.domain.type.UserGenderType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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

    private UserGenderType gender;

    private Double weight;

    private Double height;

    @NotNull(message = "지점 이름을 입력하세요.")
    private String branchName;

    @NotBlank(message = "생년월일을 입력하세요.")
    @Pattern(regexp = "^\\d{6}$", message = "생년월일은 6자리 숫자여야 합니다.")
    private String birthDate;  // 여기서 타입을 String으로 변경

    @NotBlank(message = "휴대폰 번호를 입력하세요.")
    @Pattern(regexp = "^\\d{11}$", message = "휴대폰 번호는 11자리 숫자여야 합니다.")
    private String phoneNumber;

    protected UpdateUserRequest() {}

}

