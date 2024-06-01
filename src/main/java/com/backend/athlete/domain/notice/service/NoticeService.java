package com.backend.athlete.domain.notice.service;

import com.backend.athlete.domain.notice.dto.request.SaveNoticeRequest;
import com.backend.athlete.domain.notice.dto.request.UpdateNoticeRequest;
import com.backend.athlete.domain.notice.dto.response.GetNoticeResponse;
import com.backend.athlete.domain.notice.dto.response.SaveNoticeResponse;
import com.backend.athlete.domain.notice.dto.response.UpdateNoticeResponse;
import com.backend.athlete.domain.notice.model.Notice;
import com.backend.athlete.domain.notice.repository.CommentRepository;
import com.backend.athlete.domain.notice.repository.LikeRepository;
import com.backend.athlete.domain.notice.repository.NoticeRepository;
import com.backend.athlete.domain.user.model.User;
import com.backend.athlete.domain.user.repository.UserRepository;
import com.backend.athlete.global.exception.ServiceException;
import com.backend.athlete.global.jwt.service.CustomUserDetailsImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;

    private final Path rootLocation = Paths.get("notice-images");

    public NoticeService(NoticeRepository noticeRepository, CommentRepository commentRepository, LikeRepository likeRepository, UserRepository userRepository) {
        this.noticeRepository = noticeRepository;
        this.commentRepository = commentRepository;
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
    }

    public SaveNoticeResponse saveNotice(CustomUserDetailsImpl userPrincipal, SaveNoticeRequest noticeRequest, MultipartFile file) throws IOException {
        User findUser = userRepository.findByUserId(userPrincipal.getUsername());

        if (!file.isEmpty()) {
            if (Files.notExists(rootLocation)) {
                Files.createDirectories(rootLocation);
            }
            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Files.copy(file.getInputStream(), rootLocation.resolve(filename));
            noticeRequest.setImagePath(rootLocation.resolve(filename).toString());
        }

        Notice notice = SaveNoticeRequest.toEntity(noticeRequest, findUser);
        Notice savedNotice = noticeRepository.save(notice);

        return SaveNoticeResponse.fromEntity(savedNotice);
    }

    @Transactional
    public GetNoticeResponse getNotice(Long id) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new ServiceException("존재하지 않는 게시물입니다."));

        return GetNoticeResponse.fromEntity(notice);
    }

    @Transactional
    public UpdateNoticeResponse updateNotice(Long id, CustomUserDetailsImpl userPrincipal, UpdateNoticeRequest noticeRequest, MultipartFile file) throws IOException {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notice not found"));

        if (!notice.getUser().getUserId().equals(userPrincipal.getUsername())) {
            throw new RuntimeException("You are not authorized to update this notice");
        }

        String imagePath = notice.getImagePath();
        if (!file.isEmpty()) {
            if (Files.notExists(rootLocation)) {
                Files.createDirectories(rootLocation);
            }
            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Files.copy(file.getInputStream(), rootLocation.resolve(filename));
            imagePath = rootLocation.resolve(filename).toString();
        }

        notice.updateNotice(noticeRequest.getTitle(), noticeRequest.getContent(), imagePath);

        Notice updatedNotice = noticeRepository.save(notice);

        return UpdateNoticeResponse.fromEntity(updatedNotice);
    }

    public void deleteNotice(Long id, CustomUserDetailsImpl userPrincipal) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new ServiceException("회원을 찾지 못했습니다."));

        if (!notice.getUser().getUserId().equals(userPrincipal.getUsername())) {
            throw new ServiceException("이 게시물를 삭제할 권한이 없습니다.");
        }

        noticeRepository.delete(notice);
    }

}
