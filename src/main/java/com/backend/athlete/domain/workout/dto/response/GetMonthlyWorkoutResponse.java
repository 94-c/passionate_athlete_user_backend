package com.backend.athlete.domain.workout.dto.response;

import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class GetMonthlyWorkoutResponse {
    private int count;
    private List<LocalDate> presentDays;

    public GetMonthlyWorkoutResponse(int count, List<LocalDate> presentDays) {
        this.count = count;
        this.presentDays = presentDays;
    }

}
