package com.backend.athlete.domain.user.service;

import com.backend.athlete.domain.user.dto.response.GetUserResponseDto;
import com.backend.athlete.domain.user.model.User;
import com.backend.athlete.domain.user.repository.UserRepository;
import com.backend.athlete.global.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public GetUserResponseDto getUserInfo(String userId) {
        User findUser = userRepository.findByUserId(userId);
        if (findUser == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return GetUserResponseDto.fromEntity(findUser);
    }

}
