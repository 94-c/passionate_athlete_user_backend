package com.backend.athlete.presentation.branch.response;

import com.backend.athlete.domain.branch.Branch;
import com.backend.athlete.domain.branch.data.ManagerData;
import com.backend.athlete.domain.user.User;
import lombok.Getter;

@Getter
public class BranchResponse {

    private Long id;
    private String name;
    public BranchResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    public static BranchResponse fromEntity(Branch branch) {
        return new BranchResponse(
                branch.getId(),
                branch.getName()
        );
    }

}
