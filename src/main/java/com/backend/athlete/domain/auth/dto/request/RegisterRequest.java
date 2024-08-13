package com.backend.athlete.domain.auth.dto.request;

import com.backend.athlete.domain.branch.domain.Branch;
import com.backend.athlete.domain.user.domain.Role;
import com.backend.athlete.domain.user.domain.User;
import com.backend.athlete.domain.user.domain.type.UserGenderType;
import com.backend.athlete.domain.user.domain.type.UserStatusType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter @Setter
public class RegisterRequest {

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

    @NotBlank(message = "생년월일을 입력하세요.")
    @Pattern(regexp = "^\\d{6}$", message = "생년월일은 6자리 숫자여야 합니다.")
    private String birthDate;  // 여기서 타입을 String으로 변경

    @NotBlank(message = "휴대폰 번호를 입력하세요.")
    @Pattern(regexp = "^\\d{11}$", message = "휴대폰 번호는 11자리 숫자여야 합니다.")
    private String phoneNumber;

    public static User toEntity(RegisterRequest request, Branch branch) {
        return new User(
                request.getCode(),
                request.getUserId(),
                request.getPassword(),
                request.getName(),
                request.getGender(),
                request.getWeight(),
                request.getHeight(),
                UserStatusType.ING,
                request.getRoleIds(),
                branch,
                request.getBirthDate(),
                request.getPhoneNumber()
        );
    }
}


