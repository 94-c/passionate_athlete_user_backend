package com.backend.athlete.support.util;

import com.backend.athlete.domain.notice.Notice;
import com.backend.athlete.domain.notice.NoticeRepository;
import com.backend.athlete.domain.user.User;
import com.backend.athlete.domain.user.UserRepository;
import com.backend.athlete.support.exception.ServiceException;
import com.backend.athlete.support.exception.UtilException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FindUtils {

    private static UserRepository userRepository;
    private static NoticeRepository noticeRepository;

    @Autowired
    public FindUtils(UserRepository userRepository, NoticeRepository noticeRepository) {
        FindUtils.userRepository = userRepository;
        FindUtils.noticeRepository = noticeRepository;
    }

    public static User findByUserId(String username) {
        User user = userRepository.findByUserId(username);
        if (user == null) {
            throw new UtilException("회원을 찾을 수 없습니다.");
        }
        return user;
    }
    public static User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ServiceException("해당 유저를 찾을 수 없습니다."));
    }
    public static Notice findByNoticeId(Long id) {
        return noticeRepository.findById(id)
                .orElseThrow(() -> new ServiceException("게시글이 존재 하지 않습니다."));
    }


}
