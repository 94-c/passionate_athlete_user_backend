package com.backend.athlete.domain.physical.infrastructure;

import com.backend.athlete.domain.physical.domain.Physical;

import java.time.LocalDate;
import java.util.List;

public interface PhysicalRepositoryCustom {
    List<Physical> findPhysicalRankings(LocalDate startDate, LocalDate endDate, String gender, int limit);

}
