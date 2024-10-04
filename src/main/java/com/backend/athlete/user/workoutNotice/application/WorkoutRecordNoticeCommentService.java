package com.backend.athlete.user.workoutNotice.application;

import com.backend.athlete.user.auth.jwt.service.CustomUserDetailsImpl;
import com.backend.athlete.user.user.domain.User;
import com.backend.athlete.user.workoutNotice.domain.WorkoutRecordNotice;
import com.backend.athlete.user.workoutNotice.domain.WorkoutRecordNoticeComment;
import com.backend.athlete.user.workoutNotice.domain.WorkoutRecordNoticeCommentRepository;
import com.backend.athlete.user.workoutNotice.domain.WorkoutRecordNoticeRepository;
import com.backend.athlete.user.workoutNotice.dto.request.CreateWorkoutRecordNoticeCommentRequest;
import com.backend.athlete.user.workoutNotice.dto.request.UpdateWorkoutRecordNoticeCommentRequest;
import com.backend.athlete.user.workoutNotice.dto.response.CreateWorkoutRecordNoticeCommentResponse;
import com.backend.athlete.user.workoutNotice.dto.response.GetWorkoutRecordNoticeCommentResponse;
import com.backend.athlete.user.workoutNotice.dto.response.UpdateWorkoutRecordNoticeCommentResponse;
import com.backend.athlete.support.exception.NotFoundException;
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
public class WorkoutRecordNoticeCommentService {
    private final WorkoutRecordNoticeCommentRepository workoutRecordNoticeCommentRepository;
    private final WorkoutRecordNoticeRepository workoutRecordNoticeRepository;

    @Transactional(readOnly = true)
    public Page<GetWorkoutRecordNoticeCommentResponse> findAllComments(Long noticeId, int page, int perPage) {
        Pageable pageable = PageRequest.of(page, perPage, Sort.by(Sort.Direction.DESC, "createdDate"));

        return workoutRecordNoticeCommentRepository.findByWorkoutRecordNoticeId(noticeId, pageable)
                .map(GetWorkoutRecordNoticeCommentResponse::fromEntity);
    }

    @Transactional
    public CreateWorkoutRecordNoticeCommentResponse createComment(CustomUserDetailsImpl userPrincipal, Long noticeId, CreateWorkoutRecordNoticeCommentRequest request) {
        User user = FindUtils.findByUserId(userPrincipal.getUsername());

        WorkoutRecordNotice notice = workoutRecordNoticeRepository.findById(noticeId)
                .orElseThrow(() -> new NotFoundException("공유 한 운동 기록을를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        WorkoutRecordNoticeComment newComment = workoutRecordNoticeCommentRepository.save(CreateWorkoutRecordNoticeCommentRequest.toEntity(request, notice, user));

        return CreateWorkoutRecordNoticeCommentResponse.fromEntity(newComment);
    }

    @Transactional(readOnly = true)
    public GetWorkoutRecordNoticeCommentResponse getComment(Long noticeId, Long commentId) {
        WorkoutRecordNoticeComment comment = workoutRecordNoticeCommentRepository.findByIdAndAndNoticeId(noticeId, commentId)
                .orElseThrow(() -> new NotFoundException("댓글이 존재하지 않습니다.", HttpStatus.NOT_FOUND));
        return GetWorkoutRecordNoticeCommentResponse.fromEntity(comment);
    }

    @Transactional
    public UpdateWorkoutRecordNoticeCommentResponse updateComment(CustomUserDetailsImpl userPrincipal, Long commentId, UpdateWorkoutRecordNoticeCommentRequest request) {
        WorkoutRecordNoticeComment comment = workoutRecordNoticeCommentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("댓글을 찾지 못했습니다.", HttpStatus.NOT_FOUND));

        if (!comment.getUser().getUserId().equals(userPrincipal.getUsername())) {
            throw new NotFoundException("댓글 수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        comment.updateWorkoutRecordNoticeComment(request.getContent());
        WorkoutRecordNoticeComment updatedComment = workoutRecordNoticeCommentRepository.save(comment);

        return UpdateWorkoutRecordNoticeCommentResponse.fromEntity(updatedComment);
    }

    @Transactional
    public void deleteComment(CustomUserDetailsImpl userPrincipal, Long commentId) {
        WorkoutRecordNoticeComment comment = workoutRecordNoticeCommentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("댓글을 찾지 못했습니다.", HttpStatus.NOT_FOUND));

        if (!comment.getUser().getUserId().equals(userPrincipal.getUsername())) {
            throw new NotFoundException("댓글 삭제 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        workoutRecordNoticeCommentRepository.delete(comment);
    }
}
