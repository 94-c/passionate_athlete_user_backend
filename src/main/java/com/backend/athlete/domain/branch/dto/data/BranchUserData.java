package com.backend.athlete.domain.branch.dto.data;

import com.backend.athlete.domain.user.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BranchUserData {
    private Long id;
    private String code;
    private String name;
    private String gender;
    private Double weight;
    private Double height;

    public BranchUserData(Long id, String code, String name, String gender, Double height, Double weight) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
    }

    public static BranchUserData fromEntity(User user) {
        return new BranchUserData(
                user.getId(),
                user.getCode(),
                user.getName(),
                user.getGender().toString(),
                user.getHeight(),
                user.getWeight()
        );
    }
}
