package com.backend.athlete.application;

import com.backend.athlete.domain.comment.Comment;
import com.backend.athlete.domain.comment.CommentRepository;
import com.backend.athlete.domain.notice.Notice;
import com.backend.athlete.domain.user.User;
import com.backend.athlete.presentation.comment.request.CreateCommentRequest;
import com.backend.athlete.presentation.comment.request.CreateReplyCommentRequest;
import com.backend.athlete.presentation.comment.response.CreateCommentResponse;
import com.backend.athlete.presentation.comment.response.CreateReplyCommentResponse;
import com.backend.athlete.presentation.comment.response.GetReplyCommentResponse;
import com.backend.athlete.support.exception.ServiceException;
import com.backend.athlete.support.jwt.service.CustomUserDetailsImpl;
import com.backend.athlete.support.util.FindUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReplyCommentService {
    private final CommentRepository commentRepository;

    public ReplyCommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Transactional(readOnly = true)
    public Page<GetReplyCommentResponse> findCommentByReply(Long commentId, Pageable pageable) {
        Optional<Comment> commentOptional = commentRepository.findByIdWithReplies(commentId);

        if (commentOptional.isPresent()) {
            Comment comment = commentOptional.get();
            GetReplyCommentResponse response = GetReplyCommentResponse.fromEntity(comment);
            List<GetReplyCommentResponse> replyComments = response.getReplies();

            return new PageImpl<>(replyComments, pageable, replyComments.size());
        } else {
            return Page.empty(pageable);
        }
    }

    public CreateReplyCommentResponse createReplyComment(CustomUserDetailsImpl userPrincipal, Long parentId, CreateReplyCommentRequest request) {
        User user = FindUtils.findByUserId(userPrincipal.getUsername());

        Comment parent = parentId != null ? commentRepository.findById(parentId).orElseThrow(() -> new ServiceException("해당 댓글 " + parentId + " 찾을 수 없습니다.")) : null;

        Comment createComment = commentRepository.save(CreateReplyCommentRequest.toEntity(request, user, parent));

        return CreateReplyCommentResponse.fromEntity(createComment);
    }
}
