package com.backend.athlete.user.auth.jwt.service;

import com.backend.athlete.user.user.domain.User;
import com.backend.athlete.user.auth.jwt.data.UserDetailsInfoData;
import com.backend.athlete.user.user.domain.type.UserGenderType;
import com.backend.athlete.user.user.domain.type.UserStatusType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CustomUserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    @Getter
    private UserDetailsInfoData userDetailsInfoData;

    @JsonIgnore
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetailsImpl(UserDetailsInfoData userDetailsInfoData, Collection<? extends GrantedAuthority> authorities) {
        this.userDetailsInfoData = userDetailsInfoData;
        this.authorities = authorities;
    }

    public static CustomUserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        UserDetailsInfoData userDetailsInfoData = UserDetailsInfoData.from(user);

        return new CustomUserDetailsImpl(userDetailsInfoData, authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Long getId() {
        return userDetailsInfoData.getId();
    }

    public String getCode() {
        return userDetailsInfoData.getCode();
    }

    public String getName() {
        return userDetailsInfoData.getName();
    }

    public UserGenderType getGender() {return userDetailsInfoData.getGender();}
    public UserStatusType getStatus() {
        return userDetailsInfoData.getStatus();
    }

    public String getBranchName() {
        return userDetailsInfoData.getBranchName();
    }

    @Override
    public String getPassword() {
        return userDetailsInfoData.getPassword();
    }

    @Override
    public String getUsername() {
        return userDetailsInfoData.getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CustomUserDetailsImpl user = (CustomUserDetailsImpl) o;
        return Objects.equals(userDetailsInfoData.getId(), user.userDetailsInfoData.getId());
    }
}
