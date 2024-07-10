package com.backend.athlete.domain.attendance.domain;

import com.backend.athlete.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @Comment("회원 아이디")
    private User user;

    @Column(name = "attendance_date", nullable = false)
    @Comment("출석일자")
    private LocalDate attendanceDate;

    public Attendance(User user, LocalDate attendanceDate) {
        this.user = user;
        this.attendanceDate = attendanceDate;
    }
}
