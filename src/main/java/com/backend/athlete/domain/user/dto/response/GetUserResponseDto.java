package com.backend.athlete.domain.user.dto.response;

import com.backend.athlete.domain.user.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetUserResponseDto {

    private String userId;
    private String password;
    private String name;
    private String role;
    private String createdDate;
    private String modifiedDate;
    @Builder
    public GetUserResponseDto(String userId, String password, String name, String role, String createdDate, String modifiedDate) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.role = role;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
    public static GetUserResponseDto fromEntity(User user) {
        return GetUserResponseDto.builder()
                .userId(user.getUserId())
                .password(user.getPassword())
                .name(user.getName())
                .role(user.getRoles().name())
                .createdDate(user.getCreatedDate())
                .modifiedDate(user.getModifiedDate())
                .build();
    }

}
