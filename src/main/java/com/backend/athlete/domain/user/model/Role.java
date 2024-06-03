package com.backend.athlete.domain.user.model;

import com.backend.athlete.domain.user.model.type.UserRoleType;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Getter
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("권한 인덱스")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Comment("권한 명")
    private UserRoleType name;

    protected Role() {}

}
