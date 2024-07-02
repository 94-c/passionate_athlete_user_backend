package com.backend.athlete.domain.notice;

import com.backend.athlete.support.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "notices_type")
public class NoticeType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String type;

    private String role;
    public NoticeType() {}

    public NoticeType(Long id, String type, String role) {
        this.id = id;
        this.type = type;
        this.role = role;
    }

}
