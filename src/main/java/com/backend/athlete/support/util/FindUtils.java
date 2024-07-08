package com.backend.athlete.support.util;

import com.backend.athlete.domain.notice.Notice;
import com.backend.athlete.domain.notice.NoticeRepository;
import com.backend.athlete.domain.user.domain.User;
import com.backend.athlete.domain.user.domain.UserRepository;
import com.backend.athlete.support.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
            throw new NotFoundException("해당 회원을 찾을 수 없습니다." , HttpStatus.NOT_FOUND);
        }
        return user;
    }
    public static User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("중복 되는 아이디 입니다.", HttpStatus.NOT_FOUND));
    }
    public static Notice findByNoticeId(Long id) {
        return noticeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("게시글이 존재 하지 않습니다.", HttpStatus.NOT_FOUND));
    }


}
