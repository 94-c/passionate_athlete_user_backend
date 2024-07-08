package com.backend.athlete.domain.physical;

import com.backend.athlete.domain.user.domain.User;
import com.backend.athlete.infrastructure.PhysicalRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface PhysicalRepository extends JpaRepository<Physical, Long>, PhysicalRepositoryCustom {
    @Query("SELECT COUNT(p) > 0 FROM Physical p WHERE p.user = :user AND FUNCTION('DATE', p.measureDate) = :date")
    boolean existsByUserAndMeasureDate(@Param("user") User user, @Param("date") LocalDate date);
    Physical findFirstByUserAndMeasureDateBeforeOrderByMeasureDateDesc(User user, LocalDateTime date);
    @Query("SELECT p FROM Physical p WHERE p.user = :user AND p.measureDate < :date ORDER BY p.measureDate DESC")
    Physical findTopByUserAndMeasureDateBeforeOrderByMeasureDateDesc(@Param("user") User user, @Param("date") LocalDateTime date);
    Page<Physical> findByUserIdOrderByMeasureDateDesc(Long id, Pageable pageable);
    List<Physical> findPhysicalsByUserIdAndMeasureDate(Long userId, LocalDate measureDate);
    Physical findTopByOrderByMeasureDateDesc();
    List<Physical> findByUserIdAndMeasureDateBetween(Long userId, LocalDateTime startDate, LocalDateTime endDate);
}
