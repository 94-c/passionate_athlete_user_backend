package com.backend.athlete.domain.comment.application;

import com.backend.athlete.domain.comment.domain.Comment;
import com.backend.athlete.domain.comment.domain.CommentRepository;
import com.backend.athlete.domain.notice.domain.Notice;
import com.backend.athlete.domain.notice.domain.NoticeRepository;
import com.backend.athlete.domain.user.domain.User;
import com.backend.athlete.domain.comment.dto.request.CreateCommentRequest;
import com.backend.athlete.domain.comment.dto.request.UpdateCommentRequest;
import com.backend.athlete.domain.comment.dto.response.CreateCommentResponse;
import com.backend.athlete.domain.comment.dto.response.GetCommentResponse;
import com.backend.athlete.domain.comment.dto.response.UpdateCommentResponse;
import com.backend.athlete.support.exception.NotFoundException;
import com.backend.athlete.domain.auth.jwt.service.CustomUserDetailsImpl;
import com.backend.athlete.support.util.FindUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final NoticeRepository noticeRepository;

    @Transactional(readOnly = true)
    public Page<GetCommentResponse> findAllComments(Long noticeId, int page, int perPage) {
        Pageable pageable = PageRequest.of(page, perPage, Sort.by(Sort.Direction.DESC, "createdDate"));

        return commentRepository.findByNoticeIdWithReplies(noticeId, pageable)
                .map(GetCommentResponse::fromEntity);
    }

    public CreateCommentResponse createComment(CustomUserDetailsImpl userPrincipal, Long noticeId, CreateCommentRequest request) {
        User user = FindUtils.findByUserId(userPrincipal.getUsername());

        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        Comment createComment = commentRepository.save(CreateCommentRequest.toEntity(request, notice, user));

        return CreateCommentResponse.fromEntity(createComment);
    }
    @Transactional(readOnly = true)
    public GetCommentResponse getComment(Long noticeId, Long commentId) {
        Comment comment = commentRepository.findByIdAndNoticeId(commentId, noticeId)
                .orElseThrow(() -> new NotFoundException("댓글이 존재 하지 않습니다.", HttpStatus.NOT_FOUND));
        return GetCommentResponse.fromEntity(comment);
    }

    @Transactional
    public UpdateCommentResponse updateComment(CustomUserDetailsImpl userPrincipal, Long id, UpdateCommentRequest request) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new NotFoundException("댓글을 찾지 못했습니다", HttpStatus.NOT_FOUND));
        if (!comment.getUser().getUserId().equals(userPrincipal.getUsername())) {
            throw new NotFoundException("댓글의 삭제 권힌이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }

        comment.updateComment(request.getContent());
        Comment updatedComment = commentRepository.save(comment);

        return UpdateCommentResponse.fromEntity(updatedComment);
    }

    @Transactional
    public void deleteComment(CustomUserDetailsImpl userPrincipal, Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new NotFoundException("댓글을 찾지 못했습니다", HttpStatus.NOT_FOUND));
        if (!comment.getUser().getUserId().equals(userPrincipal.getUsername())) {
            throw new NotFoundException("댓글의 삭제 권힌이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }
        deleteCommentRecursively(comment);
    }

    private void deleteCommentRecursively(Comment comment) {
        for (Comment reply : comment.getReplies()) {
            deleteCommentRecursively(reply);
        }
        commentRepository.delete(comment);
    }

}
