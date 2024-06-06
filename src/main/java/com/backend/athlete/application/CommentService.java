package com.backend.athlete.application;

import com.backend.athlete.domain.comment.Comment;
import com.backend.athlete.domain.comment.CommentRepository;
import com.backend.athlete.domain.notice.Notice;
import com.backend.athlete.domain.notice.NoticeRepository;
import com.backend.athlete.domain.user.User;
import com.backend.athlete.domain.user.UserRepository;
import com.backend.athlete.presentation.comment.request.CreateCommentRequest;
import com.backend.athlete.presentation.comment.request.UpdateCommentRequest;
import com.backend.athlete.presentation.comment.response.CreateCommentResponse;
import com.backend.athlete.presentation.comment.response.GetCommentResponse;
import com.backend.athlete.presentation.comment.response.UpdateCommentResponse;
import com.backend.athlete.support.exception.ServiceException;
import com.backend.athlete.support.jwt.service.CustomUserDetailsImpl;
import com.backend.athlete.support.util.FindUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final NoticeRepository noticeRepository;


    public CommentService(CommentRepository commentRepository, UserRepository userRepository, NoticeRepository noticeRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.noticeRepository = noticeRepository;
    }

    public CreateCommentResponse createComment(CustomUserDetailsImpl userPrincipal, Long noticeId, CreateCommentRequest request) {
        User user = FindUtil.findByUserId(userPrincipal.getUsername());

        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> new ServiceException("게시글을 찾을 수 없습니다."));
        Comment parent = request.getParentId() != null ? commentRepository.findById(request.getParentId()).orElseThrow(() -> new ServiceException("해당 댓글 " + request.getParentId() + " 찾을 수 없습니다.")) : null;

        Comment createComment = commentRepository.save(CreateCommentRequest.toEntity(request, notice, user, parent));

        return CreateCommentResponse.fromEntity(createComment);
    }
    @Transactional(readOnly = true)
    public GetCommentResponse getComment(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ServiceException("댓글이 존재 하지 않습니다."));
        return GetCommentResponse.fromEntity(comment);
    }

    @Transactional
    public UpdateCommentResponse updateComment(CustomUserDetailsImpl userPrincipal, Long id, UpdateCommentRequest request) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ServiceException("댓글을 찾지 못했습니다"));
        if (!comment.getUser().getUserId().equals(userPrincipal.getUsername())) {
            throw new RuntimeException("댓글의 삭제 권힌이 존재하지 않습니다.");
        }

        comment.updateComment(request.getContent());
        Comment updatedComment = commentRepository.save(comment);

        return UpdateCommentResponse.fromEntity(updatedComment);
    }


    @Transactional
    public void deleteComment(CustomUserDetailsImpl userPrincipal, Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ServiceException("댓글을 찾지 못했습니다"));
        if (!comment.getUser().getUserId().equals(userPrincipal.getUsername())) {
            throw new RuntimeException("댓글의 삭제 권힌이 존재하지 않습니다.");
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
