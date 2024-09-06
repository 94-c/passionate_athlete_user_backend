package com.backend.athlete.domain.workoutNotice.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WorkoutRecordNoticeCommentQueryRepository {

    @Query("SELECT c FROM WorkoutRecordNoticeComment c WHERE c.notice.id = :noticeId")
    Page<WorkoutRecordNoticeComment> findByWorkoutRecordNoticeId(@Param("noticeId") Long noticeId, Pageable pageable);

}
