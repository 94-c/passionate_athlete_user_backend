package com.backend.athlete.infrastructure;

import com.backend.athlete.domain.physical.Physical;
import com.backend.athlete.domain.physical.QPhysical;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PhysicalRepositoryImpl implements PhysicalRepositoryCustom {
}