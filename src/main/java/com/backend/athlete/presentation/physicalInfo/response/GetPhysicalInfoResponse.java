package com.backend.athlete.presentation.physicalInfo.response;

import com.backend.athlete.domain.physical.PhysicalInfo;
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
