package com.backend.athlete.domain.user.controller;

import com.backend.athlete.domain.user.dto.UserResponse;
import com.backend.athlete.domain.user.service.UserService;
import com.backend.athlete.global.jwt.CurrentUser;
import com.backend.athlete.global.jwt.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
