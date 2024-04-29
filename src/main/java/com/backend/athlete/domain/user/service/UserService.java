package com.backend.athlete.domain.user.service;

import com.backend.athlete.domain.user.domain.User;
import com.backend.athlete.domain.user.domain.enums.RoleType;
import com.backend.athlete.domain.user.dto.UpdateUserRequest;
import com.backend.athlete.domain.user.repository.UserRepository;
import com.backend.athlete.global.exception.AccessDeniedException;
import com.backend.athlete.global.exception.ResourceNotFoundException;
import com.backend.athlete.global.exception.UnauthorizedException;
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

    public User updateUser(String userId, UserPrincipal currentUser, UpdateUserRequest request) {
        User user = userRepository.getUserByUserId(userId);
        if (user.getId().equals(currentUser.getId())
                || currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleType.ADMIN.toString()))) {
            user.setName(request.name());

            return userRepository.save(user);

        }

        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You don't have permission to update profile of: " + userId);
        throw new UnauthorizedException(apiResponse);

    }


    public ApiResponse deleteUser(String userId, UserPrincipal currentUser) {
        User existingUser = userRepository.getUserByUserId(userId);
        if (existingUser == null) {
            throw new ResourceNotFoundException("User", "userId", userId);
        }

        if (!existingUser.getId().equals(currentUser.getId()) || !currentUser.getAuthorities()
                .contains(new SimpleGrantedAuthority(RoleType.ADMIN.toString()))) {
            ApiResponse apiResponse = new ApiResponse(Boolean.TRUE, "You don't have permission to delete profile of: " + userId);
            throw new AccessDeniedException(apiResponse);
        }

        userRepository.deleteById(existingUser.getId());

        return new ApiResponse(Boolean.TRUE, "You successfully deleted profile of: " + existingUser);

    }
}
