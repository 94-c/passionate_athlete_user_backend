package com.backend.athlete.domain.notice.application;

import com.backend.athlete.domain.comment.domain.Comment;
import com.backend.athlete.domain.comment.domain.CommentRepository;
import com.backend.athlete.domain.notice.domain.*;
import com.backend.athlete.domain.user.domain.User;
import com.backend.athlete.domain.notice.dto.request.PageSearchNoticeRequest;
import com.backend.athlete.domain.notice.dto.request.CreateNoticeRequest;
import com.backend.athlete.domain.notice.dto.request.UpdateNoticeRequest;
import com.backend.athlete.domain.notice.dto.response.GetNoticeResponse;
import com.backend.athlete.domain.notice.dto.response.PageSearchNoticeResponse;
import com.backend.athlete.domain.notice.dto.response.CreateNoticeResponse;
import com.backend.athlete.domain.notice.dto.response.UpdateNoticeResponse;
import com.backend.athlete.support.exception.NotFoundException;
import com.backend.athlete.domain.auth.jwt.service.CustomUserDetailsImpl;
import com.backend.athlete.support.util.FileUtils;
import com.backend.athlete.support.util.FindUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final NoticeTypeRepository noticeTypeRepository;
    private final FileRepository fileRepository;
    private final Path rootLocation = Paths.get("notice-images");

    @Transactional(readOnly = true)
    public Page<PageSearchNoticeResponse> searchNotices(PageSearchNoticeRequest request, int page, int perPage, Long kindId, boolean status) {
        Pageable pageable = PageRequest.of(page, perPage);
        Page<Notice> notices = noticeRepository.findAllByUserAndTitleAndKindAndStatus(request.getName(), request.getTitle(), pageable, kindId, status);

        return notices.map(notice -> {
            int likeCount = likeRepository.countByNoticeId(notice.getId());
            List<Comment> comments = commentRepository.findByNoticeId(notice.getId());
            return PageSearchNoticeResponse.fromEntity(notice, likeCount, comments);
        });
    }

    @Transactional(readOnly = true)
    public List<GetNoticeResponse> getAllNotices() {
        List<Notice> notices = noticeRepository.findAll();
        return notices.stream()
                .map(notice -> {
                    int likeCount = likeRepository.countByNoticeId(notice.getId());
                    return GetNoticeResponse.fromEntity(notice, likeCount);
                })
                .collect(Collectors.toList());
    }

    public CreateNoticeResponse saveNotice(CustomUserDetailsImpl userPrincipal, CreateNoticeRequest noticeRequest, List<MultipartFile> files) throws IOException {
        User user = FindUtils.findByUserId(userPrincipal.getUsername());
        NoticeType kind = noticeTypeRepository.findById(noticeRequest.getKindId()).orElseThrow(() -> new NotFoundException("Invalid notice type", HttpStatus.NOT_FOUND));

        List<File> savedFiles = new ArrayList<>();
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                String filePath = FileUtils.saveFile(file, rootLocation);
                File savedFile = new File(file.getOriginalFilename(), filePath, file.getContentType(), file.getSize());
                fileRepository.save(savedFile);
                savedFiles.add(savedFile);
            }
        }

        Notice notice = CreateNoticeRequest.toEntity(noticeRequest, user, kind, savedFiles);
        Notice savedNotice = noticeRepository.save(notice);

        return CreateNoticeResponse.fromEntity(savedNotice);
    }
    @Transactional
    public GetNoticeResponse getNotice(Long id) {
        Notice notice = FindUtils.findByNoticeId(id);
        int likeCount = likeRepository.countByNoticeId(notice.getId());
        return GetNoticeResponse.fromEntity(notice, likeCount);
    }

    @Transactional
    public UpdateNoticeResponse updateNotice(Long id, CustomUserDetailsImpl userPrincipal, UpdateNoticeRequest noticeRequest, List<MultipartFile> files) throws IOException {
        Notice notice = FindUtils.findByNoticeId(id);

        if (!notice.getUser().getUserId().equals(userPrincipal.getUsername())) {
            throw new NotFoundException("게시글의 권한이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }

        notice.updateNotice(noticeRequest.getTitle(), noticeRequest.getContent());

        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    String filePath = FileUtils.saveFile(file, rootLocation);
                    File savedFile = new File(file.getOriginalFilename(), filePath, file.getContentType(), file.getSize());
                    notice.addFile(savedFile);
                    fileRepository.save(savedFile);
                }
            }
        }

        Notice updatedNotice = noticeRepository.save(notice);

        return UpdateNoticeResponse.fromEntity(updatedNotice);
    }

    /*public void deleteNotice(Long id, CustomUserDetailsImpl userPrincipal) {
        Notice notice = FindUtils.findByNoticeId(id);

        if (!notice.getUser().getUserId().equals(userPrincipal.getUsername())) {
            throw new NotFoundException("이 게시물를 삭제할 권한이 없습니다.", HttpStatus.NOT_FOUND);
        }

        noticeRepository.delete(notice);
    }

    public GetNoticeResponse setStatus(Long id, CustomUserDetailsImpl userPrincipal) {
        Notice notice = FindUtils.findByNoticeId(id);

        if (!notice.getUser().getUserId().equals(userPrincipal.getUsername())) {
            throw new NotFoundException("이 게시물를 삭제할 권한이 없습니다.", HttpStatus.NOT_FOUND);
        }

        notice.setStatus(true);

        Notice setStatusNotice = noticeRepository.save(notice);

        return GetNoticeResponse.fromEntity(setStatusNotice);
    }*/
}
