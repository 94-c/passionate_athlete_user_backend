package com.backend.athlete.domain.user;

import com.backend.athlete.domain.branch.Branch;
import com.backend.athlete.domain.user.type.UserGenderType;
import com.backend.athlete.domain.user.type.UserStatusType;
import com.backend.athlete.support.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
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

    @Column(nullable = false)
    private String birthDate;

    @Column(nullable = false)
    private String phoneNumber;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;

    protected User() {
    }

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

    public void updateUserStatus(UserStatusType status) {
        this.status = status;
    }

    public void updateUserRole(Role role) {
        this.roles.clear();
        this.roles.add(role);
    }

}
