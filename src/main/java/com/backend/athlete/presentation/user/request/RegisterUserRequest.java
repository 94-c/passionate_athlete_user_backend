package com.backend.athlete.presentation.user.request;

import com.backend.athlete.domain.branch.Branch;
import com.backend.athlete.domain.user.Role;
import com.backend.athlete.domain.user.User;
import com.backend.athlete.domain.user.type.UserGenderType;
import com.backend.athlete.domain.user.type.UserStatusType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter @Setter
public class RegisterUserRequest {

    private String code;

    @NotBlank(message = "아이디를 입력하세요.")
    @Size(min = 4, max = 20, message = "아이디는 4자 이상 20자 이하여야 합니다.")
    private String userId;

    @NotBlank(message = "패스워드를 입력하세요.")
    @Size(min = 8, message = "패스워드는 최소 8자 이상이어야 합니다.")
    private String password;

    @NotBlank(message = "패스워드를 한번 더 입력하세요.")
    @Size(min = 8, message = "패스워드는 최소 8자 이상이어야 합니다.")
    private String passwordCheck;

    @NotBlank(message = "이름을 입력하세요.")
    private String name;

    @NotNull(message = "성별을 선택하세요.")
    private UserGenderType gender;

    @NotNull(message = "체중을 입력하세요.")
    private Double weight;

    @NotNull(message = "키를 입력하세요.")
    private Double height;

    private Set<Role> roleIds;

    @NotNull(message = "지점 이름을 입력하세요.")
    private String branchName;

    protected RegisterUserRequest() {}

    public static User toEntity(RegisterUserRequest request, Branch branch) {
        return new User(
                request.getCode(),
                request.getUserId(),
                request.getPassword(),
                request.getName(),
                request.getGender(),
                request.getWeight(),
                request.getHeight(),
                UserStatusType.WAIT,
                request.getRoleIds(),
                branch
        );
    }
}

