package com.backend.athlete.user.admin.application;

import com.backend.athlete.user.admin.dto.branch.*;
import com.backend.athlete.user.admin.dto.user.UpdateUserRoleRequest;
import com.backend.athlete.user.admin.dto.user.UpdateUserRoleResponse;
import com.backend.athlete.user.admin.dto.user.UpdateUserStatusRequest;
import com.backend.athlete.user.admin.dto.user.UpdateUserStatusResponse;
import com.backend.athlete.user.athlete.domain.Athlete;
import com.backend.athlete.user.athlete.domain.AthleteRepository;
import com.backend.athlete.user.athlete.dto.request.CreateAthleteRequest;
import com.backend.athlete.user.athlete.dto.response.CreateAthleteResponse;
import com.backend.athlete.user.auth.jwt.service.CustomUserDetailsImpl;
import com.backend.athlete.user.branch.domain.Branch;
import com.backend.athlete.user.branch.domain.BranchRepository;
import com.backend.athlete.user.user.domain.Role;
import com.backend.athlete.user.user.domain.RoleRepository;
import com.backend.athlete.user.user.domain.User;
import com.backend.athlete.user.user.domain.UserRepository;
import com.backend.athlete.user.user.domain.type.UserRoleType;
import com.backend.athlete.support.exception.NotFoundException;
import com.backend.athlete.support.exception.NotFoundRoleException;
import com.backend.athlete.support.util.FindUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AthleteRepository athleteRepository;
    private final BranchRepository branchRepository;
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

    public Page<PageSearchBranchResponse> findAllBranches(PageSearchBranchRequest request, int page, int perPage) {
        Pageable pageable = PageRequest.of(page, perPage);
        Page<Branch> branches = branchRepository.findByNameContainingAndManagerNameContaining(request.getName(), request.getManagerName(), pageable);

        return branches.map(branch -> {
            List<User> users = userRepository.findByBranchAndRole(branch, UserRoleType.USER);
            int totalMembers = users.size();
            return PageSearchBranchResponse.fromEntity(branch, totalMembers);
        });
    }

    public CreateBranchResponse createBranch(CreateBranchRequest request) {
        if (branchRepository.findByName(request.getName()).isPresent()) {
            throw new NotFoundException("지점명이 이미 존재합니다.", HttpStatus.NOT_FOUND);
        }

        User manager = FindUtils.findById(request.getManagerId());

        Branch createBranch = branchRepository.save(CreateBranchRequest.toEntity(request, manager));

        return CreateBranchResponse.fromEntity(createBranch);
    }

    public GetBranchResponse getBranch(Long id) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 지점을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        return GetBranchResponse.fromEntity(branch);
    }

    public UpdateBranchResponse updateBranch(Long id, UpdateBranchRequest request) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 지점을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        User manager = FindUtils.findById(request.getManagerId());

        branch.update(
                request.getName(),
                manager,
                request.getAddress(),
                request.getDetailedAddress(),
                request.getPostalCode(),
                request.getPhoneNumber(),
                request.getEtc()
        );

        branchRepository.save(branch);

        return UpdateBranchResponse.fromEntity(branch);
    }

    public GetBranchUsersResponse searchBranchUsersByName(String name) {
        Branch branch = branchRepository.findByNameContaining(name)
                .orElseThrow(() -> new NotFoundException("해당 지점을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        List<User> users = userRepository.findByBranchAndRole(branch, UserRoleType.USER);

        return GetBranchUsersResponse.fromEntity(branch, users);
    }
}
