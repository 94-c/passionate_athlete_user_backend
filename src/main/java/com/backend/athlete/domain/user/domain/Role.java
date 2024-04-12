package com.backend.athlete.domain.user.domain;

import com.backend.athlete.domain.user.domain.enums.RoleType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "roles")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    @Comment("권한 인덱스")
    private Long id;

    @Column(name = "role_name")
    @Comment("권한 이름")
    private RoleType roleName;

    @Builder
    public Role(RoleType roleName) {
        this.roleName = roleName;
    }

}
