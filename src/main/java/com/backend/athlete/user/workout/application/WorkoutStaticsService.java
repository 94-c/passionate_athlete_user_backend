package com.backend.athlete.user.workout.application;

import com.backend.athlete.user.attendance.domain.AttendanceRepository;
import com.backend.athlete.user.auth.jwt.service.CustomUserDetailsImpl;
import com.backend.athlete.user.exercise.domain.type.ExerciseType;
import com.backend.athlete.user.user.domain.User;
import com.backend.athlete.user.workout.domain.WorkoutRecord;
import com.backend.athlete.user.workout.domain.WorkoutRecordHistoryRepository;
import com.backend.athlete.user.workout.domain.WorkoutRecordRepository;
import com.backend.athlete.user.workout.dto.response.GetExerciseTypeLastWeightResponse;
import com.backend.athlete.user.workout.dto.response.GetWorkoutStatisticsResponse;
import com.backend.athlete.support.util.FindUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkoutStaticsService {
    private final WorkoutRecordRepository workoutRecordRepository;
    private final AttendanceRepository attendanceRepository;
    private final WorkoutRecordHistoryRepository workoutRecordHistoryRepository;

    public GetWorkoutStatisticsResponse getWorkoutStatistics(CustomUserDetailsImpl userPrincipal) {
        User user = FindUtils.findByUserId(userPrincipal.getUsername());
        long totalDuration = getTotalWorkoutDuration(user);
        int totalCount = getTotalWorkoutCount(user);
        String averageIntensity = getAverageIntensity(user);
        String maxRecord = getMaxWorkoutRecord(user);
        double weeklySuccessRate = getWeeklySuccessRate(user);
        double monthlySuccessRate = getMonthlySuccessRate(user);
        double weeklyAttendanceRate = getWeeklyAttendanceRate(user);
        double monthlyAttendanceRate = getMonthlyAttendanceRate(user);

        return new GetWorkoutStatisticsResponse(
                totalDuration,
                totalCount,
                averageIntensity,
                maxRecord,
                weeklySuccessRate,
                monthlySuccessRate,
                weeklyAttendanceRate,
                monthlyAttendanceRate
        );
    }

    public List<GetExerciseTypeLastWeightResponse> getLastWeightsByExerciseType(CustomUserDetailsImpl userPrincipal, ExerciseType exerciseType) {
        User user = FindUtils.findByUserId(userPrincipal.getUsername());
        return workoutRecordHistoryRepository.findLastWeightsByExerciseType(user.getId(), exerciseType);
    }

    private long getTotalWorkoutDuration(User user) {
        List<WorkoutRecord> records = workoutRecordRepository.findByUserId(user.getId());
        return records.stream()
                .mapToLong(record -> {
                    if (record.getDuration() == null) {
                        System.out.println("Duration is null for record id: " + record.getId());
                        return 0L;
                    }
                    return parseDuration(record.getDuration());
                }).sum();
    }

    private int getTotalWorkoutCount(User user) {
        return workoutRecordRepository.countByUserId(user.getId());
    }

    private String getAverageIntensity(User user) {
        List<WorkoutRecord> records = workoutRecordRepository.findByUserId(user.getId());
        if (records.isEmpty()) {
            System.out.println("No workout records found for user id: " + user.getId());
            return null;
        }
        return records.stream()
                .filter(record -> record.getRating() != null)
                .collect(Collectors.groupingBy(WorkoutRecord::getRating, Collectors.counting()))
                .entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse(null);
    }

    private String getMaxWorkoutRecord(User user) {
        return workoutRecordRepository.findTopByUserIdOrderByDurationDesc(user.getId())
                .map(WorkoutRecord::getDuration).orElse(null);
    }

    private double getWeeklySuccessRate(User user) {
        LocalDate today = LocalDate.now();
        LocalDate weekStart = today.minusDays(7);
        List<WorkoutRecord> records = workoutRecordRepository.findByUserIdAndCreatedAtBetween(user.getId(), weekStart.atStartOfDay(), today.atTime(23, 59, 59));
        if (records.isEmpty()) {
            System.out.println("No workout records found for user id: " + user.getId() + " in the past week");
            return 0;
        }
        long successCount = records.stream().filter(record -> Boolean.TRUE.equals(record.getSuccess())).count();
        return (double) successCount / records.size() * 100;
    }

    private double getMonthlySuccessRate(User user) {
        LocalDate today = LocalDate.now();
        LocalDate monthStart = today.minusDays(30);
        List<WorkoutRecord> records = workoutRecordRepository.findByUserIdAndCreatedAtBetween(user.getId(), monthStart.atStartOfDay(), today.atTime(23, 59, 59));
        if (records.isEmpty()) {
            System.out.println("No workout records found for user id: " + user.getId() + " in the past month");
            return 0;
        }
        long successCount = records.stream().filter(record -> Boolean.TRUE.equals(record.getSuccess())).count();
        return (double) successCount / records.size() * 100;
    }

    private double getWeeklyAttendanceRate(User user) {
        LocalDate today = LocalDate.now();
        LocalDate weekStart = today.minusDays(7);
        long attendanceCount = attendanceRepository.countByUserIdAndAttendanceDateBetween(user.getId(), weekStart, today);
        return (double) attendanceCount / 7 * 100;
    }

    private double getMonthlyAttendanceRate(User user) {
        LocalDate today = LocalDate.now();
        LocalDate monthStart = today.minusDays(30);
        long attendanceCount = attendanceRepository.countByUserIdAndAttendanceDateBetween(user.getId(), monthStart, today);
        return (double) attendanceCount / 30 * 100;
    }

    private long parseDuration(String duration) {
        if (duration == null || duration.isEmpty()) {
            System.out.println("Duration string is null or empty");
            return 0;
        }
        String[] parts = duration.split(":");
        if (parts.length < 2) {
            System.out.println("Invalid duration format: " + duration);
            return 0;
        }
        int minutes = Integer.parseInt(parts[0]);
        int seconds = Integer.parseInt(parts[1]);
        return minutes * 60 + seconds;
    }
}


