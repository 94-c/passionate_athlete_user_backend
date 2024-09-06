package com.backend.athlete.domain.physical.domain;

import com.backend.athlete.domain.physical.infrastructure.PhysicalQueryRepository;
import com.backend.athlete.domain.user.domain.User;
import com.backend.athlete.domain.physical.infrastructure.PhysicalRepositoryCustom;
import com.backend.athlete.domain.user.domain.type.UserGenderType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface PhysicalRepository extends JpaRepository<Physical, Long>, PhysicalRepositoryCustom, PhysicalQueryRepository {
    Physical findFirstByUserAndMeasureDateBeforeOrderByMeasureDateDesc(User user, LocalDateTime date);
    Page<Physical> findByUserIdOrderByMeasureDateDesc(Long id, Pageable pageable);
    List<Physical> findPhysicalsByUserIdAndMeasureDate(Long userId, LocalDate measureDate);
    Physical findTopByUserIdOrderByMeasureDateDesc(Long userId);
    List<Physical> findByUserAndMeasureDateAfterOrderByMeasureDateAsc(User user, LocalDateTime measureDate);
    Page<Physical> findByUserId(Long userId, Pageable pageable);
}
