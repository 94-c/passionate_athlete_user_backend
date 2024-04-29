package com.backend.athlete.domain.user.domain;

import com.backend.athlete.domain.user.domain.enums.UserStatus;
import com.backend.athlete.global.domain.AuditingFields;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

import java.util.*;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
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
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'WAIT'")
    @Comment("인증 상태")
    private UserStatus status;

    @Builder
    private User(String userId, String name, String password, List<Role> roles, UserStatus status) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.roles = roles;
        this.status = status;
    }

    public List<Role> getRoles() {

        return roles == null ? null : new ArrayList<>(roles);
    }

    public void setRoles(List<Role> roles) {

        if (roles == null) {
            this.roles = null;
        } else {
            this.roles = Collections.unmodifiableList(roles);
        }
    }


}
