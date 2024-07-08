package com.backend.athlete.domain.attendance;

import com.backend.athlete.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;

@Getter
@Entity
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

    protected Attendance() {}

    public Attendance(User user, LocalDate attendanceDate) {
        this.user = user;
        this.attendanceDate = attendanceDate;
    }
}
