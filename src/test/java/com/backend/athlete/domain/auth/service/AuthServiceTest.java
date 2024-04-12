package com.backend.athlete.domain.auth.service;

import com.backend.athlete.domain.auth.dto.SignUpRequest;
import com.backend.athlete.domain.auth.repository.AuthRepository;
import com.backend.athlete.domain.user.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AuthRepository authRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @Test
    public void signUp_NewUser_Success() {
        // Given
        SignUpRequest request = new SignUpRequest("newUser", "New User", "password");
        when(authRepository.findByUserId("newUser")).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        // When
        authService.signUp(request);

        // Then
        verify(authRepository, times(1)).save(any(User.class));
    }

    @Test
    public void signUp_ExistingUser_ExceptionThrown() {
        // Given
        SignUpRequest request = new SignUpRequest("existingUser", "Existing User", "password");
        User existingUser = User.builder().userId("existingUser").build();
        when(authRepository.findByUserId("existingUser")).thenReturn(Optional.of(existingUser));

        // When, Then
        assertThrows(RuntimeException.class, () -> authService.signUp(request));
        verify(authRepository, never()).save(any(User.class));
    }
}