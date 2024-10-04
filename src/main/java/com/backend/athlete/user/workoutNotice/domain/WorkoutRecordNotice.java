package com.backend.athlete.user.workoutNotice.domain;

import com.backend.athlete.user.workout.domain.WorkoutRecord;
import com.backend.athlete.support.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "workout_record_notice")
public class WorkoutRecordNotice extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "workout_record_id", nullable = false)
    private WorkoutRecord workoutRecord;

    @Comment("공개 여부")
    @Column(name = "is_shared")
    private Boolean isShared;

    @Comment("공유된 날짜")
    @Column(name = "shared_at")
    private LocalDateTime sharedAt;

    public WorkoutRecordNotice(WorkoutRecord workoutRecord, Boolean isShared, LocalDateTime sharedAt) {
        this.workoutRecord = workoutRecord;
        this.isShared = isShared;
        this.sharedAt = sharedAt;
    }

}
