package com.backend.athlete.domain.admin.dto.branch;

import com.backend.athlete.domain.branch.domain.Branch;
import com.backend.athlete.domain.branch.data.BranchUserData;
import com.backend.athlete.domain.user.domain.User;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class GetBranchUsersResponse {
    private Long id;
    private String branchName;
    private String managerName;
    private int totalMembers;
    private List<BranchUserData> users;

    public GetBranchUsersResponse(Long id, String branchName, String managerName, int totalMembers, List<BranchUserData> users) {
        this.id = id;
        this.branchName = branchName;
        this.managerName = managerName;
        this.totalMembers = totalMembers;
        this.users = users;
    }
    public static GetBranchUsersResponse fromEntity(Branch branch, List<User> users) {
        List<BranchUserData> userData = users.stream()
                .map(BranchUserData::fromEntity)
                .collect(Collectors.toList());
        return new GetBranchUsersResponse(
                branch.getId(),
                branch.getName(),
                branch.getManager().getName(),
                users.size(),
                userData
        );
    }

}
