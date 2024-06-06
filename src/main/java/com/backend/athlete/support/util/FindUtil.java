package com.backend.athlete.support.util;

import com.backend.athlete.domain.user.User;
import com.backend.athlete.domain.user.UserRepository;
import com.backend.athlete.support.exception.UtilException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FindUtil {

    private static UserRepository userRepository;

    @Autowired
    public FindUtil(UserRepository userRepository) {
        FindUtil.userRepository = userRepository;
    }

    public static User findByUserId(String username) {
        User user = userRepository.findByUserId(username);
        if (user == null) {
            throw new UtilException("회원을 찾을 수 없습니다.");
        }
        return user;
    }


}
