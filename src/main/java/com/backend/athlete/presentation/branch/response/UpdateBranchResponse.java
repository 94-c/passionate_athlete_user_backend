package com.backend.athlete.presentation.branch.response;

import com.backend.athlete.domain.branch.Branch;
import com.backend.athlete.domain.branch.data.ManagerData;
import com.backend.athlete.domain.user.domain.User;
import lombok.Getter;

@Getter
public class UpdateBranchResponse {
    private Long id;
    private String name;
    private ManagerData manager;
    private String address;
    private String detailedAddress;
    private String postalCode;
    private String phoneNumber;
    private String etc;

    public UpdateBranchResponse(Long id, String name, ManagerData manager, String address, String detailedAddress, String postalCode, String phoneNumber, String etc) {
        this.id = id;
        this.name = name;
        this.manager = manager;
        this.address = address;
        this.detailedAddress = detailedAddress;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.etc = etc;
    }
    public static UpdateBranchResponse fromEntity(Branch branch) {
        User manager = branch.getManager();
        ManagerData managerDTO = manager != null ? ManagerData.fromEntity(manager) : null;

        return new UpdateBranchResponse(
                branch.getId(),
                branch.getName(),
                managerDTO,
                branch.getAddress(),
                branch.getDetailedAddress(),
                branch.getPostalCode(),
                branch.getPhoneNumber(),
                branch.getEtc()
        );
    }
}
