package com.backend.athlete.domain.physical.domain;

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
import java.util.List;
import java.util.Optional;

public interface PhysicalRepository extends JpaRepository<Physical, Long>, PhysicalRepositoryCustom {
    @Query("SELECT COUNT(p) > 0 FROM Physical p WHERE p.user = :user AND FUNCTION('DATE', p.measureDate) = :date")
    boolean existsByUserAndMeasureDate(@Param("user") User user, @Param("date") LocalDate date);
    Physical findFirstByUserAndMeasureDateBeforeOrderByMeasureDateDesc(User user, LocalDateTime date);
    @Query("SELECT p FROM Physical p WHERE p.user = :user AND p.measureDate < :date ORDER BY p.measureDate DESC")
    Physical findTopByUserAndMeasureDateBeforeOrderByMeasureDateDesc(@Param("user") User user, @Param("date") LocalDateTime date);
    Page<Physical> findByUserIdOrderByMeasureDateDesc(Long id, Pageable pageable);
    List<Physical> findPhysicalsByUserIdAndMeasureDate(Long userId, LocalDate measureDate);
    Physical findTopByOrderByMeasureDateDesc();
    @Query("SELECT p FROM Physical p WHERE p.user.id = :userId AND p.measureDate BETWEEN :startDate AND :endDate ORDER BY p.measureDate DESC LIMIT 1")
    Physical findPhysicalByBodyFatMassChangeInDateRange(@Param("userId") Long userId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT p FROM Physical p WHERE (:branchId IS NULL OR p.user.branch.id = :branchId) AND (:gender IS NULL OR p.user.gender = :gender) AND p.measureDate BETWEEN :startDateTime AND :endDateTime ORDER BY p.bodyFatMassChange DESC LIMIT 1")
    List<Physical> findTopRankingsByBranchAndDateRange(
            @Param("branchId") Long branchId,
            @Param("gender") UserGenderType gender,
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime,
            Pageable pageable
    );

    @Query("SELECT p FROM Physical p WHERE (:gender IS NULL OR p.user.gender = :gender) AND p.measureDate BETWEEN :startDateTime AND :endDateTime ORDER BY p.bodyFatMassChange DESC LIMIT 1")
    List<Physical> findTopRankingsByDateRange(
            @Param("gender") UserGenderType gender,
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime,
            Pageable pageable
    );

}
