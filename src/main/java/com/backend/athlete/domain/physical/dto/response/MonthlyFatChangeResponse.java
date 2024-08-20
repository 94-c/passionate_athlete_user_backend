package com.backend.athlete.domain.physical.dto.response;

import com.backend.athlete.domain.physical.domain.Physical;
import lombok.Getter;

@Getter
public class MonthlyFatChangeResponse {
    private double fatChange;

    public MonthlyFatChangeResponse(double fatChange) {
        this.fatChange = fatChange;
    }

    public static MonthlyFatChangeResponse fromEntity(Physical physical) {
        return new MonthlyFatChangeResponse(physical.getBodyFatMassChange());
    }
}
