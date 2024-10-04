package com.backend.athlete.user.branch.dto.response;

import com.backend.athlete.user.branch.domain.Branch;
import lombok.Getter;

@Getter
public class FindAllBranchResponse {

    private Long id;
    private String name;
    public FindAllBranchResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    public static FindAllBranchResponse fromEntity(Branch branch) {
        return new FindAllBranchResponse(
                branch.getId(),
                branch.getName()
        );
    }

}
