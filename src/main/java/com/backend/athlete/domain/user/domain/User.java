package com.backend.athlete.domain.user.domain;

import com.backend.athlete.global.domain.AuditingFields;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    @Comment("회원 인덱스")
    private Long id;

    @Column(name = "user_id")
    @Comment("회원 아이디")
    private String userId;

    @Comment("회원 패스워드")
    private String password;

    @Comment("회원 이름")
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @Comment("회원 권한")
    private Set<Role> roles = new HashSet<>();

    @Builder
    private User(String userId, String name, String password, Set<Role> roles) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.roles = roles;
    }


}
