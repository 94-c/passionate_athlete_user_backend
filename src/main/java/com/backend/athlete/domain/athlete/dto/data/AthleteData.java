package com.backend.athlete.domain.athlete.dto.data;

import com.backend.athlete.domain.athlete.model.type.AthleteSuccessType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter @Setter
public class AthleteData {
    private LocalDate dailyTime;
    private String athletics;
    private AthleteSuccessType type;
    private BigDecimal totalRecord;
    private long count;
    private String etc;
    private String username;

    public LocalTime getTotalRecordAsLocalTime() {
        return LocalTime.ofSecondOfDay(totalRecord.longValue());
    }
}
