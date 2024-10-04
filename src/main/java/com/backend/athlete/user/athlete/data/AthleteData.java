package com.backend.athlete.user.athlete.data;

import com.backend.athlete.user.athlete.domain.type.AthleteSuccessType;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class AthleteData {

    private LocalDate dailyTime;
    private String athletics;
    private AthleteSuccessType type;
    private Integer totalRecord;
    private long count;
    private String etc;
    private String username;
    public LocalTime getTotalRecordAsLocalTime() {
        return LocalTime.ofSecondOfDay(totalRecord.longValue());
    }

    public AthleteData(LocalDate dailyTime, String athletics, AthleteSuccessType type, Integer totalRecord, long count, String etc, String username) {
        this.dailyTime = dailyTime;
        this.athletics = athletics;
        this.type = type;
        this.totalRecord = totalRecord;
        this.count = count;
        this.etc = etc;
        this.username = username;
    }
}
