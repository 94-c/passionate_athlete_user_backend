package com.backend.athlete.domain.workout.application;

import com.backend.athlete.domain.auth.jwt.service.CustomUserDetailsImpl;
import com.backend.athlete.domain.execise.domain.Exercise;
import com.backend.athlete.domain.execise.domain.ExerciseRepository;
import com.backend.athlete.domain.user.domain.User;
import com.backend.athlete.domain.user.domain.type.UserGenderType;
import com.backend.athlete.domain.workout.domain.*;
import com.backend.athlete.domain.workout.dto.request.CreateWorkoutRecordRequest;
import com.backend.athlete.domain.workout.dto.request.WorkoutHistoryRequest;
import com.backend.athlete.domain.workout.dto.response.CreateWorkoutRecordResponse;
import com.backend.athlete.domain.workout.dto.response.WorkoutRecordStatisticsResponse;
import com.backend.athlete.support.exception.NotFoundException;
import com.backend.athlete.support.util.FindUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkoutRecordService {
    private final WorkoutRecordRepository workoutRecordRepository;
    private final ScheduledWorkoutRepository scheduledWorkoutRepository;
    private final WorkoutRecordHistoryRepository workoutRecordHistoryRepository;
    private final ExerciseRepository exerciseRepository;

    public CreateWorkoutRecordResponse saveWorkoutRecord(CreateWorkoutRecordRequest request, CustomUserDetailsImpl userPrincipal) {
        User user = FindUtils.findByUserId(userPrincipal.getUsername());

        ScheduledWorkout scheduledWorkout = null;
        if (request.getScheduledWorkoutId() != null) {
            scheduledWorkout = scheduledWorkoutRepository.findById(request.getScheduledWorkoutId())
                    .orElseThrow(() -> new NotFoundException("오늘의 운동을 찾을 수가 없습니다.", HttpStatus.NOT_FOUND));
        }

        WorkoutRecord workoutRecord = CreateWorkoutRecordRequest.toEntity(request, user, scheduledWorkout);
        WorkoutRecord savedWorkoutRecord = workoutRecordRepository.save(workoutRecord);

        for (WorkoutHistoryRequest historyRequest : request.getWorkoutHistories()) {
            Exercise exercise = exerciseRepository.findById(historyRequest.getExerciseId())
                    .orElseThrow(() -> new NotFoundException("운동을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
            WorkoutRecordHistory history = WorkoutHistoryRequest.toEntity(historyRequest, user, exercise);
            history.setWorkoutRecord(savedWorkoutRecord);
            workoutRecordHistoryRepository.save(history);
        }

        return CreateWorkoutRecordResponse.fromEntity(savedWorkoutRecord);
    }

    public Page<WorkoutRecordStatisticsResponse> getMainWorkoutRecordsByDateRangeAndGender(LocalDate date, UserGenderType gender, String rating, int page, int perPage) {
        Pageable pageable = PageRequest.of(page, perPage, Sort.by(Sort.Direction.ASC, "duration"));

        LocalDateTime startDate = date.atTime(16, 0, 0);
        LocalDateTime endDate = date.plusDays(1).atTime(13, 0, 0);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String startDateTime = startDate.format(formatter);
        String endDateTime = endDate.format(formatter);

        Page<WorkoutRecord> records = workoutRecordRepository.findMainWorkoutRecordsByDateRangeAndGenderAndRating(startDateTime, endDateTime, gender, rating, pageable);

        return records.map(WorkoutRecordStatisticsResponse::fromEntity);
    }

}
