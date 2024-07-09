package com.backend.athlete.domain.branch.dto.response;

import com.backend.athlete.domain.branch.domain.Branch;
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
