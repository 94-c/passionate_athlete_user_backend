package com.backend.athlete.domain.execise.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Getter
@Entity
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
    protected Exercise() {}

    public Exercise(String name, String description, String link) {
        this.name = name;
        this.description = description;
        this.link = link;
    }

    public void update(String name, String description, String link) {
        this.name = name;
        this.description = description;
        this.link = link;
    }

}
