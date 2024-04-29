package com.backend.athlete.domain.user.controller;

import com.backend.athlete.domain.user.domain.User;
import com.backend.athlete.domain.user.dto.UpdateUserRequest;
import com.backend.athlete.domain.user.dto.UpdateUserResponse;
import com.backend.athlete.domain.user.dto.UserResponse;
import com.backend.athlete.domain.user.dto.data.UpdateUserData;
import com.backend.athlete.domain.user.service.UserService;
import com.backend.athlete.global.jwt.CurrentUser;
import com.backend.athlete.global.jwt.UserPrincipal;
import com.backend.athlete.global.payload.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserResponse> getCurrentUser(
            @CurrentUser UserPrincipal currentUser
    ) {
        UserPrincipal user =  userService.getCurrentUser(currentUser);

        UserResponse response = UserResponse.fromUserPrincipal(user);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<User> updateUser(
            @Valid @RequestBody UpdateUserRequest request,
            @PathVariable(value = "userId") String userId,
            @CurrentUser UserPrincipal currentUser
    ) {
        User updateUser = userService.updateUser(userId, currentUser, request);

        return new ResponseEntity<>(updateUser, HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteUser (
            @PathVariable(value = "userId") String userId,
            @CurrentUser UserPrincipal currentUser
    ) {
        ApiResponse response = userService.deleteUser(userId, currentUser);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
