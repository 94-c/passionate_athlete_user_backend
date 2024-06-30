package com.backend.athlete.support.jwt.service;

import com.backend.athlete.domain.user.User;
import com.backend.athlete.domain.user.data.UserDetailsInfo;
import com.backend.athlete.domain.user.type.UserGenderType;
import com.backend.athlete.domain.user.type.UserStatusType;
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
    private UserDetailsInfo userDetailsInfo;

    @JsonIgnore
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetailsImpl(UserDetailsInfo userDetailsInfo, Collection<? extends GrantedAuthority> authorities) {
        this.userDetailsInfo = userDetailsInfo;
        this.authorities = authorities;
    }

    public static CustomUserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        UserDetailsInfo userDetailsInfo = UserDetailsInfo.from(user);

        return new CustomUserDetailsImpl(userDetailsInfo, authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Long getId() {
        return userDetailsInfo.getId();
    }

    public String getCode() {
        return userDetailsInfo.getCode();
    }

    public String getName() {
        return userDetailsInfo.getName();
    }

    public UserGenderType getGender() {return userDetailsInfo.getGender();}
    public UserStatusType getStatus() {
        return userDetailsInfo.getStatus();
    }

    public String getBranchName() {
        return userDetailsInfo.getBranchName();
    }

    @Override
    public String getPassword() {
        return userDetailsInfo.getPassword();
    }

    @Override
    public String getUsername() {
        return userDetailsInfo.getUserId();
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
        return Objects.equals(userDetailsInfo.getId(), user.userDetailsInfo.getId());
    }
}
