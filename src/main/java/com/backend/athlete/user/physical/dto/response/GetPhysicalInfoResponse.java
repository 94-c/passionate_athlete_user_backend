package com.backend.athlete.user.physical.dto.response;

import com.backend.athlete.user.physical.domain.PhysicalInfo;
import lombok.Getter;

@Getter
public class GetPhysicalInfoResponse {

    private String term;
    private String description;

    public GetPhysicalInfoResponse(String term, String description) {
        this.term = term;
        this.description = description;
    }

    public static GetPhysicalInfoResponse fromEntity(PhysicalInfo physicalInfo) {
        return new GetPhysicalInfoResponse(
                physicalInfo.getTerm(),
                physicalInfo.getDescription()
        );
    }

}
