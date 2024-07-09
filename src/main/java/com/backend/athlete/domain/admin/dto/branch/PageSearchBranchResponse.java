package com.backend.athlete.domain.admin.dto.branch;

import com.backend.athlete.domain.branch.domain.Branch;
import lombok.Getter;

@Getter
public class PageSearchBranchResponse {
    private Long id;
    private String name;
    private String address;
    private String detailedAddress;
    private String postalCode;
    private String phoneNumber;
    private String etc;
    private int totalUsers;

    public PageSearchBranchResponse(Long id, String name, String address, String detailedAddress, String postalCode, String phoneNumber, String etc, int totalUsers) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.detailedAddress = detailedAddress;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.etc = etc;
        this.totalUsers = totalUsers;
    }
    public static PageSearchBranchResponse fromEntity(Branch branch, int totalUsers) {

        return new PageSearchBranchResponse(
                branch.getId(),
                branch.getName(),
                branch.getAddress(),
                branch.getDetailedAddress(),
                branch.getPostalCode(),
                branch.getPhoneNumber(),
                branch.getEtc(),
                totalUsers
        );
    }

}