package com.backend.athlete.user.auth.jwt.data;

import com.backend.athlete.user.user.domain.User;
import com.backend.athlete.user.user.domain.type.UserGenderType;
import com.backend.athlete.user.user.domain.type.UserStatusType;
import lombok.Getter;

@Getter
public class UserDetailsInfoData {
    private Long id;
    private String code;
    private String userId;
    private String name;
    private String password;
    private UserStatusType status;
    private UserGenderType gender;
    private String branchName;

    public UserDetailsInfoData(Long id, String code, String userId, String name, String password, UserStatusType status, UserGenderType gender, String branchName) {
        this.id = id;
        this.code = code;
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.status = status;
        this.gender = gender;
        this.branchName = branchName;
    }

    public static UserDetailsInfoData from(User user) {
        return new UserDetailsInfoData(
                user.getId(),
                user.getCode(),
                user.getUserId(),
                user.getName(),
                user.getPassword(),
                user.getStatus(),
                user.getGender(),
                user.getBranch() != null ? user.getBranch().getName() : null
        );
    }
}
