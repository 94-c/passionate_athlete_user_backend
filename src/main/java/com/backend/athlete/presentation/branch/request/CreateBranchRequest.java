package com.backend.athlete.presentation.branch.request;

import com.backend.athlete.domain.branch.Branch;
import com.backend.athlete.domain.user.domain.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateBranchRequest {

    @NotNull(message = "지점명을 입력 해주세요.")
    @Size(max = 100, message = "지점명은 10자 이내여야 합니다.")
    private String name;

    @Size(max = 255, message = "주소는 255자 이내여야 합니다.")
    private String address;

    @Size(max = 255, message = "상세주소는 255자 이내여야 합니다.")
    private String detailedAddress;

    @Size(max = 10, message = "우편번호는 10자 이내여야 합니다.")
    private String postalCode;

    @Size(max = 20, message = "전화번호는 20자 이내여야 합니다.")
    private String phoneNumber;

    private String etc;

    @NotNull(message = "담당 회원 ID를 입력 해주세요.")
    private Long managerId;

    public CreateBranchRequest() {
    }
    public static Branch toEntity(CreateBranchRequest request, User user) {
        return new Branch(
                request.getName(),
                user,
                request.getAddress(),
                request.getDetailedAddress(),
                request.getPostalCode(),
                request.getPhoneNumber(),
                request.getEtc()
        );
    }
}
