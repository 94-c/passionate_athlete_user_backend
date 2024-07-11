package com.backend.athlete.domain.physical.dto.response;

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
