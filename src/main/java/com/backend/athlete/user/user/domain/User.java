package com.backend.athlete.user.user.domain;

import com.backend.athlete.user.branch.domain.Branch;
import com.backend.athlete.user.user.domain.type.UserGenderType;
import com.backend.athlete.user.user.domain.type.UserStatusType;
import com.backend.athlete.support.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name = "birth_date", nullable = false)
    @Comment("생년월일")
    private String birthDate;

    @Column(name = "phone_number", nullable = false)
    @Comment("휴대폰 번호")
    private String phoneNumber;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;

    // 회원 가입
    public User(String code, String userId, String password, String name, UserGenderType gender, Double weight, Double height, UserStatusType status, Set<Role> roles, Branch branch, String birthDate, String phoneNumber) {
        this.code = code;
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.status = status;
        this.roles = roles;
        this.branch = branch;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
    }

    public User(String userId, String name, String phoneNumber) {
        this.userId = userId;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public void updateUser(String password, UserGenderType gender, Double weight, Double height, Branch branch, String birthDate, String phoneNumber) {
        this.password = password;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.branch = branch;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
    }

    public void updatePhysicalAttributes(Double weight, Double height) {
        this.weight = weight;
        this.height = height;
    }

    public void updateUserStatus(UserStatusType status) {
        this.status = status;
    }

    public void updateUserRole(Role role) {
        this.roles.clear();
        this.roles.add(role);
    }

    public void setPassword(String encodedPassword) {
        this.password = encodedPassword;
    }
}
