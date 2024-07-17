package com.backend.athlete.domain.execise.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "exercises")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("운동명")
    @Column(unique = true)
    private String name;

    @Comment("운동 설명")
    private String description;

    @Comment("운동 영상")
    private String link;

    @Enumerated(EnumType.STRING)
    @Comment("운동 타입")
    private ExerciseType type;

    public Exercise(String name, String description, String link, ExerciseType type) {
        this.name = name;
        this.description = description;
        this.link = link;
        this.type = type;
    }
}

