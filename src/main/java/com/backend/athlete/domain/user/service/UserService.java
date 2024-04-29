package com.backend.athlete.domain.user.service;

import com.backend.athlete.domain.user.domain.User;
import com.backend.athlete.domain.user.domain.enums.RoleType;
import com.backend.athlete.domain.user.dto.UpdateUserRequest;
import com.backend.athlete.domain.user.dto.data.UpdateUserData;
import com.backend.athlete.domain.user.repository.UserRepository;
import com.backend.athlete.global.exception.AccessDeniedException;
import com.backend.athlete.global.exception.ResourceNotFoundException;
import com.backend.athlete.global.jwt.UserPrincipal;
import com.backend.athlete.global.payload.ApiResponse;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;


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


    public ApiResponse deleteUser(String userId, UserPrincipal currentUser) {
        User existingUser = userRepository.getUserByUserId(userId);
        if (existingUser == null) {
            throw new ResourceNotFoundException("User", "userId", userId);
        }

        if (!existingUser.getId().equals(currentUser.getId()) || !currentUser.getAuthorities()
                .contains(new SimpleGrantedAuthority(RoleType.ADMIN.name()))) {
            ApiResponse apiResponse = new ApiResponse(Boolean.TRUE, "You don't have permission to delete profile of: " + userId);
            throw new AccessDeniedException(apiResponse);
        }

        userRepository.deleteById(existingUser.getId());

        return new ApiResponse(Boolean.TRUE, "You successfully deleted profile of: " + existingUser);

    }
}
