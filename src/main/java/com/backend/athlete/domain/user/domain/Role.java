package com.backend.athlete.domain.user.domain;

import com.backend.athlete.domain.user.domain.enums.RoleType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.HashSet;
import java.util.Set;

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
    @Enumerated(EnumType.STRING)
    @Comment("권한 이름")
    private RoleType roleName;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    @Builder
    public Role(RoleType roleName) {
        this.roleName = roleName;
    }

}
