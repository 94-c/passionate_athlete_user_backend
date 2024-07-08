package com.backend.athlete.domain.user.domain;

import com.backend.athlete.domain.user.domain.type.UserRoleType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("권한 인덱스")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Comment("권한 명")
    private UserRoleType name;

}
