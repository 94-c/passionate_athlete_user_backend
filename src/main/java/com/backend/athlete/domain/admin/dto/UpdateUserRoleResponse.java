package com.backend.athlete.domain.admin.dto;

import com.backend.athlete.domain.user.domain.Role;
import com.backend.athlete.domain.user.domain.User;
import com.backend.athlete.domain.user.domain.type.UserRoleType;
import lombok.Getter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class UpdateUserRoleResponse {
    private Long id;
    private String userId;
    private String userName;
    private Set<UserRoleType> roles;
    public UpdateUserRoleResponse(Long id, String userId, String userName, Set<UserRoleType> roles) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.roles = roles;
    }
    public static UpdateUserRoleResponse fromEntity(User user) {
        return new UpdateUserRoleResponse(
                user.getId(),
                user.getUserId(),
                user.getName(),
                user.getRoles().stream().map(Role::getName).collect(Collectors.toSet())
        );
    }

}
