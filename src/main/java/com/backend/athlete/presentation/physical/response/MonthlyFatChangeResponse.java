package com.backend.athlete.presentation.physical.response;

import com.backend.athlete.domain.physical.Physical;
import lombok.Getter;

@Getter
public class MonthlyFatChangeResponse {
    private double fatChange;

    public MonthlyFatChangeResponse(double fatChange) {
        this.fatChange = fatChange;
    }

    public static MonthlyFatChangeResponse fromEntity(double fatChange) {
        return new MonthlyFatChangeResponse(
                fatChange
        );
    }
}
