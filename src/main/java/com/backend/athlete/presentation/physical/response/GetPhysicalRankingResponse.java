package com.backend.athlete.presentation.physical.response;

import com.backend.athlete.domain.physical.Physical;
import com.backend.athlete.domain.user.domain.User;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class GetPhysicalRankingResponse {

    private Long id;
    private String username;
    private String branchName;
    private String gender;
    private Double bodyFatMassChange;
    private LocalDate startDate;
    private LocalDate endDate;

    public GetPhysicalRankingResponse(Long id, String username, String branchName, String gender, Double bodyFatMassChange, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.username = username;
        this.branchName = branchName;
        this.gender = gender;
        this.bodyFatMassChange = bodyFatMassChange;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static GetPhysicalRankingResponse fromEntity(Physical physical, LocalDate startDate, LocalDate endDate) {
        User findUser = physical.getUser();
        return new GetPhysicalRankingResponse(
                physical.getId(),
                findUser.getName(),
                findUser.getBranch().getName(),
                findUser.getGender().toString(),
                physical.getBodyFatMassChange(),
                startDate,
                endDate
        );
    }
}
