package com.backend.athlete.domain.workoutNotice.application;

import com.backend.athlete.domain.auth.jwt.service.CustomUserDetailsImpl;
import com.backend.athlete.domain.user.domain.User;
import com.backend.athlete.domain.workout.domain.WorkoutRecord;
import com.backend.athlete.domain.workout.domain.WorkoutRecordHistory;
import com.backend.athlete.domain.workout.domain.WorkoutRecordHistoryRepository;
import com.backend.athlete.domain.workout.domain.WorkoutRecordRepository;
import com.backend.athlete.domain.workout.dto.response.GetWorkoutRecordAndHistoryResponse;
import com.backend.athlete.domain.workout.dto.response.WorkoutRecordHistoryResponse;
import com.backend.athlete.domain.workoutNotice.domain.WorkoutRecordNotice;
import com.backend.athlete.domain.workoutNotice.domain.WorkoutRecordNoticeRepository;
import com.backend.athlete.domain.workoutNotice.dto.response.CreateWorkoutRecordNoticeResponse;
import com.backend.athlete.support.util.FindUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkoutRecordNoticeService {
    private final WorkoutRecordRepository workoutRecordRepository;
    private final WorkoutRecordHistoryRepository workoutRecordHistoryRepository;
    private final WorkoutRecordNoticeRepository workoutRecordNoticeRepository;

    @Transactional(readOnly = true)
    public Page<GetWorkoutRecordAndHistoryResponse> getNonSharedWorkoutRecords(CustomUserDetailsImpl userPrincipal, int page, int perPage) {
        Pageable pageable = PageRequest.of(page, perPage);
        User user = FindUtils.findByUserId(userPrincipal.getUsername());

        Page<WorkoutRecord> workoutRecords = workoutRecordRepository.findByUserIdAndIsSharedFalse(user.getId(), pageable);

        return workoutRecords.map(workoutRecord -> {
            List<WorkoutRecordHistory> histories = workoutRecordHistoryRepository.findByWorkoutRecord(workoutRecord);
            List<WorkoutRecordHistoryResponse> historyResponses = histories.stream()
                    .map(WorkoutRecordHistoryResponse::fromEntity)
                    .collect(Collectors.toList());

            return GetWorkoutRecordAndHistoryResponse.fromEntity(workoutRecord, historyResponses);
        });
    }



    @Transactional
    public CreateWorkoutRecordNoticeResponse createWorkoutRecordNotice(Long workoutRecordId, CustomUserDetailsImpl userPrincipal) {
        User user = FindUtils.findByUserId(userPrincipal.getUsername());

        WorkoutRecord workoutRecord = workoutRecordRepository.findById(workoutRecordId)
                .orElseThrow(() -> new IllegalArgumentException("해당 운동 기록을 찾을 수 없습니다."));

        WorkoutRecordNotice workoutRecordNotice = new WorkoutRecordNotice(
                workoutRecord,
                true,
                LocalDateTime.now()
        );

        workoutRecordNoticeRepository.save(workoutRecordNotice);

        List<WorkoutRecordHistory> histories = workoutRecordHistoryRepository.findByWorkoutRecord(workoutRecord);

        List<WorkoutRecordHistoryResponse> historyResponses = histories.stream()
                .map(WorkoutRecordHistoryResponse::fromEntity)
                .collect(Collectors.toList());

        return CreateWorkoutRecordNoticeResponse.fromEntity(workoutRecordNotice, workoutRecord, historyResponses);
    }

}
