package com.backend.athlete.domain.workoutNotice.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkoutRecordNoticeCommentRepository extends JpaRepository<WorkoutRecordNoticeComment, Long>, WorkoutRecordNoticeCommentQueryRepository {

    Optional<WorkoutRecordNoticeComment> findByIdAndAndNoticeId(Long noticeId, Long id);

}
