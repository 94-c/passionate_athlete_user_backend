package com.backend.athlete.domain.user.service;

import com.backend.athlete.global.jwt.UserPrincipal;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public UserPrincipal getCurrentUser(UserPrincipal currentUser) {
        return currentUser;
    }

}
