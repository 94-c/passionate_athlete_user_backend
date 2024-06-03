package com.backend.athlete.domain.branch.dto.data;

import com.backend.athlete.domain.user.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ManagerData {

    private Long id;
    private String userId;
    private String userName;

    public ManagerData(Long id, String userId, String userName) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
    }
    public static ManagerData fromEntity(User manager) {
        return new ManagerData(
                manager.getId(),
                manager.getUserId(),
                manager.getName()
        );
    }

}
