package com.backend.athlete.presentation.physical.response;

import com.backend.athlete.domain.physical.Physical;
import com.backend.athlete.domain.user.User;
import lombok.Getter;

@Getter
public class GetPhysicalRankingResponse {

    private Long id;
    private String username;
    private Double bodyFatMassChange;

    public GetPhysicalRankingResponse(Long id, String username, Double bodyFatMassChange) {
        this.id = id;
        this.username = username;
        this.bodyFatMassChange = bodyFatMassChange;
    }

    // Entity -> DTO
    public static GetPhysicalRankingResponse fromEntity(Physical physical) {
        User findUser = physical.getUser();
        return new GetPhysicalRankingResponse(
                physical.getId(),
                findUser.getName(),
                physical.getBodyFatMassChange()
        );
    }

}
