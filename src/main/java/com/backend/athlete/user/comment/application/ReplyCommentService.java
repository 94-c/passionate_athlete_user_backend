package com.backend.athlete.user.comment.application;

import com.backend.athlete.user.comment.domain.Comment;
import com.backend.athlete.user.comment.domain.CommentRepository;
import com.backend.athlete.user.notice.domain.Notice;
import com.backend.athlete.user.notice.domain.NoticeRepository;
import com.backend.athlete.user.user.domain.User;
import com.backend.athlete.user.comment.dto.request.CreateReplyCommentRequest;
import com.backend.athlete.user.comment.dto.request.UpdateCommentRequest;
import com.backend.athlete.user.comment.dto.response.CreateReplyCommentResponse;
import com.backend.athlete.user.comment.dto.response.GetReplyCommentResponse;
import com.backend.athlete.support.exception.NotFoundException;
import com.backend.athlete.user.auth.jwt.service.CustomUserDetailsImpl;
import com.backend.athlete.support.util.FindUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReplyCommentService {
    private final CommentRepository commentRepository;
    private final NoticeRepository noticeRepository;

    public Page<GetReplyCommentResponse> getReplies(Long commentId, int page, int perPage) {
        Pageable pageable = PageRequest.of(page, perPage, Sort.by(Sort.Direction.DESC, "createdDate"));
        Comment parentComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("댓글을 찾지 못했습니다." , HttpStatus.NOT_FOUND));

        Page<Comment> replies = commentRepository.findByParent(parentComment, pageable);
        return replies.map(GetReplyCommentResponse::fromEntity);
    }

    public CreateReplyCommentResponse createReplyComment(CustomUserDetailsImpl userPrincipal, Long parentId, CreateReplyCommentRequest request) {
        User user = FindUtils.findByUserId(userPrincipal.getUsername());

        Comment parent = parentId != null ? commentRepository.findById(parentId).orElseThrow(() -> new NotFoundException("해당 댓글 " + parentId + " 찾을 수 없습니다." , HttpStatus.NOT_FOUND)) : null;
        Notice notice = noticeRepository.findById(request.getNoticeId()).orElseThrow(() -> new NotFoundException("해당 게시글 " + request.getNoticeId() + " 찾을 수 없습니다.",  HttpStatus.NOT_FOUND));

        Comment createComment = commentRepository.save(CreateReplyCommentRequest.toEntity(request, user, parent, notice));

        return CreateReplyCommentResponse.fromEntity(createComment);
    }

    public GetReplyCommentResponse updateReplyComment(CustomUserDetailsImpl userPrincipal, Long commentId, Long replyId, UpdateCommentRequest request) {
        Comment reply = commentRepository.findByIdAndParentId(replyId, commentId)
                .orElseThrow(() -> new NotFoundException("대댓글을 찾지 못했습니다." , HttpStatus.NOT_FOUND));

        if (!reply.getUser().getId().equals(userPrincipal.getId())) {
            throw new NotFoundException("해당 대댓글을 수정할 권한이 없습니다." , HttpStatus.NOT_FOUND);
        }

        request.setContent(reply.getContent());
        Comment updatedReply = commentRepository.save(reply);

        return GetReplyCommentResponse.fromEntity(updatedReply);
    }

    public void deleteReplyComment(CustomUserDetailsImpl userPrincipal, Long commentId, Long replyId) {
        Comment reply = commentRepository.findByIdAndParentId(replyId, commentId)
                .orElseThrow(() -> new NotFoundException("대댓글을 찾지 못했습니다.", HttpStatus.NOT_FOUND));

        if (!reply.getUser().getId().equals(userPrincipal.getId())) {
            throw new NotFoundException("해당 대댓글을 삭제할 권한이 없습니다.", HttpStatus.NOT_FOUND);
        }
        commentRepository.delete(reply);
    }
}
