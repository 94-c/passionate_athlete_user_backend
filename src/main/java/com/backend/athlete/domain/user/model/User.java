package com.backend.athlete.domain.user.model;

import com.backend.athlete.domain.user.model.type.UserStatusType;
import com.backend.athlete.domain.user.model.type.UserGenderType;
import com.backend.athlete.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@Table(name = "users")
public class User extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("회원 인덱스")
    private Long id;

    @Comment("회원 코드")
    private String code;

    @Column(name = "user_id", nullable = false)
    @Comment("아이디")
    private String userId;

    @Column(nullable = false)
    @Comment("패스워드")
    private String password;

    @Comment("이름")
    private String name;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'NONE'")
    @Comment("성별")
    private UserGenderType gender;

    @Comment("체중")
    private Double weight;

    @Comment("키")
    private Double height;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'WAIT'")
    @Comment("회원 상태")
    private UserStatusType status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

    protected User() {}

    // 회원 가입
    public User(String code, String userId, String password, String name, UserGenderType gender, Double weight, Double height, UserStatusType status, Set<Role> roles) {
        this.code = code;
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.status = status;
        this.roles = roles;
    }

    // 회원 수정
    public User(String password, UserGenderType gender, Double weight, Double height, UserStatusType status) {
        this.password = password;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.status = status;
    }

    public void updateUser(String password, UserGenderType gender, Double weight, Double height) {
        this.password = password;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
    }

    public void updatePhysicalAttributes(Double weight, Double height) {
        this.weight = weight;
        this.height = height;
    }
}
