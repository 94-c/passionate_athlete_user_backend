package com.backend.athlete.domain.auth.dto.response;

import com.backend.athlete.global.jwt.service.CustomUserDetailsImpl;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class LoginTokenResponseDto {

    private String type = "Bearer";
    private String token;
    private UserDto users;

    public LoginTokenResponseDto(String type, String token, UserDto users) {
        this.type = type;
        this.token = token;
        this.users = users;
    }

    // Entity -> Dto
    public static LoginTokenResponseDto fromEntity(CustomUserDetailsImpl userDetails, String token) {
        Set<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        UserDto userDto = new UserDto(
                userDetails.getCode(),
                userDetails.getUsername(),
                userDetails.getName(),
                roles
        );

        return new LoginTokenResponseDto(
                "Bearer",
                token,
                userDto
        );
    }


    /**
     * 회원 정보를 따로 구분 하기 위함
     */
    @Getter @Setter
    public static class UserDto {

        private String code;
        private String userId;
        private String name;
        private Set<String> roles;

        public UserDto(String code, String userId, String name, Set<String> roles) {
            this.code = code;
            this.userId = userId;
            this.name = name;
            this.roles = roles;
        }

    }
}
