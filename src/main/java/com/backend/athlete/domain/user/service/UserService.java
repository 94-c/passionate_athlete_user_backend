package com.backend.athlete.domain.user.service;

import com.backend.athlete.domain.user.domain.User;
import com.backend.athlete.domain.user.dto.UpdateUserRequest;
import com.backend.athlete.domain.user.dto.data.UpdateUserData;
import com.backend.athlete.domain.user.repository.UserRepository;
import com.backend.athlete.global.exception.ResourceNotFoundException;
import com.backend.athlete.global.jwt.UserPrincipal;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserPrincipal getCurrentUser(UserPrincipal currentUser) {
        return currentUser;
    }

    public UpdateUserData updateUser(String userId, UserPrincipal currentUser, UpdateUserRequest request) {
        User existingUser = userRepository.getUserByUserId(userId);
        if (existingUser == null) {
            throw new ResourceNotFoundException("User", "userId", userId);
        }

        if (!existingUser.getId().equals(currentUser.getId())) {
            throw new ResourceNotFoundException("User", "userId", userId);
        }

        User updateUserInfo = User.builder()
                .name(request.name())
                .build();

        User updatedUser = userRepository.save(updateUserInfo);

        return UpdateUserData.builder()
                .user(updatedUser)
                .build();
    }


}
