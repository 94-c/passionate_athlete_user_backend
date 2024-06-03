package com.backend.athlete.domain.auth.dto.request;

import com.backend.athlete.domain.branch.model.Branch;
import com.backend.athlete.domain.user.model.Role;
import com.backend.athlete.domain.user.model.User;
import com.backend.athlete.domain.user.model.type.UserGenderType;
import com.backend.athlete.domain.user.model.type.UserStatusType;
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

    @NotBlank(message = "지점 이름을 입력하세요.")
    private String branchName;

    protected RegisterUserRequest() {}

    public static User toEntity(RegisterUserRequest dto, Branch branch) {
        return new User(
                dto.getCode(),
                dto.getUserId(),
                dto.getPassword(),
                dto.getName(),
                dto.getGender(),
                dto.getWeight(),
                dto.getHeight(),
                UserStatusType.WAIT,
                dto.getRoleIds(),
                branch
        );
    }
}

