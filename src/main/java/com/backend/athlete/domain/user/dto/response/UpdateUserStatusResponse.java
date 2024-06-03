package com.backend.athlete.domain.user.dto.response;

import com.backend.athlete.domain.user.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateUserStatusResponse {

    private Long id;
    private String userId;
    private String userName;
    private String status;

    public UpdateUserStatusResponse(Long id, String userId, String userName, String status) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.status = status;
    }

    public static UpdateUserStatusResponse fromEntity(User user) {
        return new UpdateUserStatusResponse(
                user.getId(),
                user.getUserId(),
                user.getName(),
                user.getStatus().toString()
        );
    }

}
