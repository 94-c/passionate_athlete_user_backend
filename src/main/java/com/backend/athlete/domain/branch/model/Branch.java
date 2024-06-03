package com.backend.athlete.domain.branch.model;

import com.backend.athlete.domain.user.model.User;
import com.backend.athlete.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;

import java.util.Set;

@Getter
@Entity
@Table(name = "branch_offices")
public class Branch extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("지점 인덱스")
    private Long id;

    @Comment("지점명")
    private String name;

    @OneToOne
    @JoinColumn(name = "manager_id")
    @Comment("지점 헤드 코치")
    private User manager;

    @Comment("주소")
    private String address;

    @Comment("상세주소")
    @Column(name = "detailed_address")
    private String detailedAddress;

    @Comment("우편번호")
    @Column(name = "postal_code")
    private String postalCode;

    @Comment("전화번호")
    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "etc")
    private String etc;

    @OneToMany(mappedBy = "branch")
    private Set<User> users;

    protected Branch() {}

    public Branch(String name, User manager, String address, String detailedAddress, String postalCode, String phoneNumber, String etc) {
        this.name = name;
        this.manager = manager;
        this.address = address;
        this.detailedAddress = detailedAddress;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.etc = etc;
    }

    public void update(String name, User manager, String address, String detailedAddress, String postalCode, String phoneNumber, String etc) {
        this.name = name;
        this.manager = manager;
        this.address = address;
        this.detailedAddress = detailedAddress;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.etc = etc;
    }




}
