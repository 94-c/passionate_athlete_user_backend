package com.backend.athlete.user.workout.application;

import com.backend.athlete.user.attendance.domain.AttendanceRepository;
import com.backend.athlete.user.attendance.dto.request.CreateAttendanceRequest;
import com.backend.athlete.user.auth.jwt.service.CustomUserDetailsImpl;
import com.backend.athlete.user.exercise.domain.Exercise;
import com.backend.athlete.user.exercise.domain.ExerciseRepository;
import com.backend.athlete.user.user.domain.User;
import com.backend.athlete.user.user.domain.type.UserGenderType;
import com.backend.athlete.user.workout.domain.*;
import com.backend.athlete.user.workout.domain.data.WorkoutRecordCalendarData;
import com.backend.athlete.user.workout.domain.type.WorkoutMode;
import com.backend.athlete.user.workout.domain.type.WorkoutRecordType;
import com.backend.athlete.user.workout.dto.request.CreateWorkoutRecordRequest;
import com.backend.athlete.user.workout.dto.request.WorkoutHistoryRequest;
import com.backend.athlete.user.workout.dto.response.*;
import com.backend.athlete.user.workoutNotice.domain.WorkoutRecordNoticeCommentRepository;
import com.backend.athlete.user.workoutNotice.domain.WorkoutRecordNoticeRepository;
import com.backend.athlete.support.exception.NotFoundException;
import com.backend.athlete.support.util.FindUtils;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkoutRecordService {
    private final WorkoutRecordRepository workoutRecordRepository;
    private final ScheduledWorkoutRepository scheduledWorkoutRepository;
    private final ExerciseRepository exerciseRepository;
    private final AttendanceRepository attendanceRepository;
    private final WorkoutRecordNoticeRepository workoutRecordNoticeRepository;
    private final WorkoutRecordHistoryRepository   workoutRecordHistoryRepository;
    private final WorkoutRecordNoticeCommentRepository  workoutRecordNoticeCommentRepository;


    public CreateWorkoutRecordResponse saveWorkoutRecord(CreateWorkoutRecordRequest request, CustomUserDetailsImpl userPrincipal) {
        User user = FindUtils.findByUserId(userPrincipal.getUsername());
        WorkoutRecordType exerciseType = request.getExerciseType();

        ScheduledWorkout scheduledWorkout = validateScheduledWorkout(exerciseType, request.getScheduledWorkoutId());

        WorkoutRecord savedWorkoutRecord = saveWorkoutRecordEntity(request, user, scheduledWorkout);

        saveWorkoutHistory(request, user, savedWorkoutRecord);

        LocalDateTime now = LocalDateTime.now();
        LocalTime cutoffTime = LocalTime.of(15, 0);

        LocalDate eventDate = now.toLocalTime().isBefore(cutoffTime) ? now.toLocalDate().minusDays(1) : now.toLocalDate();

        markAttendanceIfNotExists(user, eventDate);

        return CreateWorkoutRecordResponse.fromEntity(savedWorkoutRecord);
    }


    private void markAttendanceIfNotExists(User user, LocalDate eventDate) {
        if (attendanceRepository.findByUserIdAndAttendanceDate(user.getId(), eventDate).isEmpty()) {
            CreateAttendanceRequest attendanceRequest = new CreateAttendanceRequest(eventDate);

            attendanceRepository.save(CreateAttendanceRequest.toEntity(attendanceRequest, user));
        }
    }


    private ScheduledWorkout validateScheduledWorkout(WorkoutRecordType exerciseType, Long scheduledWorkoutId) {
        if (exerciseType == WorkoutRecordType.MAIN) {
            if (scheduledWorkoutId == null) {
                throw new ServiceException("본운동의 경우 스케줄 ID가 필수입니다.");
            }
            return scheduledWorkoutRepository.findById(scheduledWorkoutId)
                    .orElseThrow(() -> new NotFoundException("오늘의 운동을 찾을 수가 없습니다.", HttpStatus.NOT_FOUND));
        } else {
            if (scheduledWorkoutId != null) {
                throw new ServiceException("변형 또는 추가 운동의 경우 스케줄 ID가 없어야 합니다.");
            }
            return null;
        }
    }

    private WorkoutRecord saveWorkoutRecordEntity(CreateWorkoutRecordRequest request, User user, ScheduledWorkout scheduledWorkout) {
        WorkoutRecord workoutRecord = CreateWorkoutRecordRequest.toEntity(request, user, scheduledWorkout);
        return workoutRecordRepository.save(workoutRecord);
    }

    private void saveWorkoutHistory(CreateWorkoutRecordRequest request, User user, WorkoutRecord workoutRecord) {
        if (request.getWorkoutDetails() != null) {
            for (WorkoutHistoryRequest historyRequest : request.getWorkoutDetails()) {
                Exercise exercise = exerciseRepository.findByName(historyRequest.getExerciseName())
                        .orElseThrow(() -> new NotFoundException("운동을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
                WorkoutRecordHistory history = WorkoutHistoryRequest.toEntity(historyRequest, user, exercise);
                workoutRecord.addWorkoutHistory(history);
            }
        }
    }

    @Transactional
    public Page<WorkoutRecordStatisticsResponse> getMainWorkoutRecordsByDateRangeAndGender(LocalDate date, UserGenderType gender, String rating, int page, int perPage) {
        Pageable pageable = PageRequest.of(page, perPage);
        LocalDateTime startDate = date.atTime(15, 0, 0);
        LocalDateTime endDate = date.plusDays(1).atTime(15, 59, 59);

        List<ScheduledWorkout> workouts = scheduledWorkoutRepository.findByScheduledDateTimeBetween(startDate, endDate);

        if (workouts.isEmpty()) {
            throw new IllegalArgumentException("해당 날짜에 운동 스케줄이 없습니다.");
        }

        ScheduledWorkout workout = workouts.get(0);  // 적절히 선택된 운동
        WorkoutMode workoutMode = workout.getWorkoutMode();

        Page<WorkoutRecord> records;
        if (workoutMode == WorkoutMode.ROUND_RANKING) {
            if (rating == null || rating.isEmpty()) {
                records = workoutRecordRepository.findSuccessfulRecordsSortedByRoundsWithoutRating(startDate, endDate, gender, pageable);
            } else {
                records = workoutRecordRepository.findSuccessfulRecordsSortedByRounds(startDate, endDate, gender, rating, pageable);
            }
        } else {
            if (rating == null || rating.isEmpty()) {
                records = workoutRecordRepository.findSuccessfulRecordsSortedByDurationWithoutRating(startDate, endDate, gender, pageable);
            } else {
                records = workoutRecordRepository.findSuccessfulRecordsSortedByDuration(startDate, endDate, gender, rating, pageable);
            }
        }

        return records.map(WorkoutRecordStatisticsResponse::fromEntity);
    }

    @Transactional
    public Page<WorkoutRecordStatisticsResponse> getWorkoutRankingsByDate(LocalDateTime startDate, LocalDateTime endDate, UserGenderType gender, String rating, int page, int perPage) {
        Pageable pageable = PageRequest.of(page, perPage);

        Page<WorkoutRecord> records;
        if (rating == null || rating.isEmpty()) {
            records = workoutRecordRepository.findSuccessfulRecordsSortedByRoundsWithoutRating(startDate, endDate, gender, pageable);
        } else {
            records = workoutRecordRepository.findSuccessfulRecordsSortedByDuration(startDate, endDate, gender, rating, pageable);
        }

        return records.map(WorkoutRecordStatisticsResponse::fromEntity);
    }

    @Transactional
    public GetMonthlyWorkoutResponse getMonthlyWorkoutCounts(YearMonth month, CustomUserDetailsImpl userPrincipal) {
        User user = FindUtils.findByUserId(userPrincipal.getUsername());
        LocalDateTime startDate = month.atDay(1).atStartOfDay();
        LocalDateTime endDate = month.atEndOfMonth().atTime(LocalTime.MAX);

        List<WorkoutRecord> workoutRecords = workoutRecordRepository.findByUserAndCreatedAtBetween(user, startDate, endDate);

        List<LocalDate> presentDays = workoutRecords.stream()
                .map(record -> {
                    LocalDateTime createdAt = record.getCreatedAt();
                    if (createdAt.getHour() < 15) {
                        return createdAt.toLocalDate().minusDays(1);
                    } else {
                        return createdAt.toLocalDate();
                    }
                })
                .distinct()
                .collect(Collectors.toList());

        LocalDateTime now = LocalDateTime.now();
        if (now.getHour() < 15) {
            presentDays = presentDays.stream()
                    .filter(date -> date.isBefore(now.toLocalDate()))
                    .collect(Collectors.toList());
        }

        return new GetMonthlyWorkoutResponse(presentDays.size(), presentDays);
    }


    @Transactional
    public GetDailyWorkoutRecordResponse getDailyWorkoutRecord(LocalDate date, CustomUserDetailsImpl userPrincipal) {
        User user = FindUtils.findByUserId(userPrincipal.getUsername());
        LocalDateTime adjustedStartDate = date.atTime(15, 0);
        LocalDateTime adjustedEndDate = date.plusDays(1).atTime(14, 59, 59, 999999999);

        List<WorkoutRecord> records = workoutRecordRepository.findByUserAndCreatedAtBetween(
                user, adjustedStartDate, adjustedEndDate);

        List<WorkoutRecordCalendarData> recordDTOs = records.stream()
                .map(WorkoutRecordCalendarData::new)
                .toList();

        return new GetDailyWorkoutRecordResponse(recordDTOs);
    }


    @Transactional
    public void deleteWorkoutRecord(CustomUserDetailsImpl userPrincipal, Long recordId) {
        User user = FindUtils.findByUserId(userPrincipal.getUsername());
        WorkoutRecord workoutRecord = workoutRecordRepository.findById(recordId)
                .orElseThrow(() -> new NotFoundException("운동 기록을 찾을 수 없습니다. ID: " + recordId, HttpStatus.NOT_FOUND));

        if (!workoutRecord.getUser().getId().equals(user.getId())) {
            throw new SecurityException("해당 운동 기록을 삭제할 권한이 없습니다.");
        }

        workoutRecordHistoryRepository.deleteByWorkoutRecord(workoutRecord);

        workoutRecordNoticeRepository.findByWorkoutRecord(workoutRecord)
                .ifPresent(notice -> {
                    workoutRecordNoticeCommentRepository.deleteByNotice(notice);
                    workoutRecordNoticeRepository.delete(notice);
                });

        workoutRecordRepository.delete(workoutRecord);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime createdDateTime = LocalDateTime.parse(workoutRecord.getCreatedDate(), formatter);

        LocalDateTime startDate = createdDateTime.toLocalDate().atStartOfDay();
        LocalDateTime endDate = startDate.plusDays(1).minusSeconds(1);

        boolean exists = workoutRecordRepository.existsByUserAndCreatedDateBetween(user.getId(), startDate, endDate) > 0;

        if (!exists) {
            attendanceRepository.deleteByUserAndAttendanceDate(user, createdDateTime.toLocalDate());
        }
    }


}
