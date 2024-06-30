package com.backend.athlete.domain.physical;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Getter
@Entity
@Table(name = "physicals_info")
public class PhysicalInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("용어")
    private String term;

    @Comment("상세 설명")
    private String description;

    protected PhysicalInfo() {}

}
