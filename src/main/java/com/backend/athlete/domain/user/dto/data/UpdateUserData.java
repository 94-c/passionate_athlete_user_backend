package com.backend.athlete.domain.user.dto.data;

import com.backend.athlete.domain.user.domain.Role;
import com.backend.athlete.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class UpdateUserData {

    private User updateUser;

    @Builder
    public UpdateUserData(User user){
        this.updateUser = user;
    }


}
