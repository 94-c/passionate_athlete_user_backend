package com.backend.athlete.domain.branch.dto.response;

import com.backend.athlete.domain.branch.dto.data.BranchUserData;
import com.backend.athlete.domain.branch.model.Branch;
import com.backend.athlete.domain.user.model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
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
