package com.backend.athlete.domain.branch.dto.response;

import com.backend.athlete.domain.branch.model.Branch;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateBranchResponse {

    private Long id;
    private String name;
    private Long managerId;
    private String managerName;
    private String address;
    private String detailedAddress;
    private String postalCode;
    private String phoneNumber;
    private String etc;

    public CreateBranchResponse(Long id, String name, Long managerId, String managerName, String address, String detailedAddress, String postalCode, String phoneNumber, String etc) {
        this.id = id;
        this.name = name;
        this.managerId = managerId;
        this.managerName = managerName;
        this.address = address;
        this.detailedAddress = detailedAddress;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.etc = etc;
    }

    public static CreateBranchResponse fromEntity(Branch branch) {
        return new CreateBranchResponse(
                branch.getId(),
                branch.getName(),
                branch.getManager().getId(),
                branch.getManager().getName(),
                branch.getAddress(),
                branch.getDetailedAddress(),
                branch.getPostalCode(),
                branch.getPhoneNumber(),
                branch.getEtc()
        );
    }

}
