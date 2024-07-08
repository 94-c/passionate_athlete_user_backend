package com.backend.athlete.domain.admin.application;

import com.backend.athlete.domain.admin.dto.UpdateUserRoleRequest;
import com.backend.athlete.domain.admin.dto.UpdateUserRoleResponse;
import com.backend.athlete.domain.admin.dto.UpdateUserStatusRequest;
import com.backend.athlete.domain.admin.dto.UpdateUserStatusResponse;
import com.backend.athlete.domain.athlete.domain.Athlete;
import com.backend.athlete.domain.athlete.domain.AthleteRepository;
import com.backend.athlete.domain.athlete.dto.request.CreateAthleteRequest;
import com.backend.athlete.domain.athlete.dto.response.CreateAthleteResponse;
import com.backend.athlete.domain.auth.jwt.service.CustomUserDetailsImpl;
import com.backend.athlete.domain.user.domain.Role;
import com.backend.athlete.domain.user.domain.RoleRepository;
import com.backend.athlete.domain.user.domain.User;
import com.backend.athlete.domain.user.domain.UserRepository;
import com.backend.athlete.support.exception.NotFoundRoleException;
import com.backend.athlete.support.util.FindUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AthleteRepository athleteRepository;
    public UpdateUserStatusResponse updateUserStatus(Long userId, UpdateUserStatusRequest request) {
        User user = FindUtils.findById(userId);
        user.updateUserStatus(
                request.getStatus()
        );
        userRepository.save(user);
        return UpdateUserStatusResponse.fromEntity(user);
    }

    public UpdateUserRoleResponse updateUserRole(Long userId, UpdateUserRoleRequest request) {
        User user = FindUtils.findById(userId);

        Role newRole = roleRepository.findByName(request.getRole())
                .orElseThrow(() -> new NotFoundRoleException(HttpStatus.NOT_FOUND));

        user.updateUserRole(newRole);
        User updatedUser = userRepository.save(user);

        return UpdateUserRoleResponse.fromEntity(updatedUser);
    }

    public CreateAthleteResponse createAthlete(CustomUserDetailsImpl userPrincipal, CreateAthleteRequest request) {
        User user = FindUtils.findByUserId(userPrincipal.getUsername());

        request.setDailyTime(LocalDate.now());

        Athlete createAthlete = athleteRepository.save(CreateAthleteRequest.toEntity(request, user));

        return CreateAthleteResponse.fromEntity(createAthlete);
    }
}
