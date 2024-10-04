package com.backend.athlete.user.workout.application;

import com.backend.athlete.user.workout.domain.WorkoutRecord;
import com.backend.athlete.user.workout.domain.WorkoutRecordHistoryRepository;
import com.backend.athlete.user.workout.domain.WorkoutRecordRepository;
import com.backend.athlete.user.workout.dto.response.GetWorkoutRecordAndHistoryResponse;
import com.backend.athlete.user.workout.dto.response.WorkoutRecordHistoryResponse;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkoutRecordHistoryService {
    private final WorkoutRecordRepository workoutRecordRepository;
    private final WorkoutRecordHistoryRepository workoutRecordHistoryRepository;

    public GetWorkoutRecordAndHistoryResponse getRecordWithHistories(Long workoutRecordId) {
        WorkoutRecord workoutRecord = workoutRecordRepository.findById(workoutRecordId)
                .orElseThrow(() -> new ServiceException("해당 운동 기록이 없습니다."));
        List<WorkoutRecordHistoryResponse> histories = workoutRecordHistoryRepository.findByWorkoutRecordId(workoutRecordId)
                .stream()
                .map(WorkoutRecordHistoryResponse::fromEntity)
                .collect(Collectors.toList());
        return GetWorkoutRecordAndHistoryResponse.fromEntity(workoutRecord, histories);
    }
}
