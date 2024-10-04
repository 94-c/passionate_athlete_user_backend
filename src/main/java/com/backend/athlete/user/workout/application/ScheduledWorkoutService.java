package com.backend.athlete.user.workout.application;

import com.backend.athlete.user.exercise.domain.Exercise;
import com.backend.athlete.user.exercise.domain.ExerciseRepository;
import com.backend.athlete.user.workout.domain.*;
import com.backend.athlete.user.workout.dto.request.CreateScheduledWorkoutRequest;
import com.backend.athlete.user.workout.dto.request.WorkoutInfoRequest;
import com.backend.athlete.user.workout.dto.request.WorkoutRatingRequest;
import com.backend.athlete.user.workout.dto.response.CreateScheduledWorkoutResponse;
import com.backend.athlete.user.workout.dto.response.GetScheduledWorkoutResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduledWorkoutService {
    private final ScheduledWorkoutRepository scheduledWorkoutRepository;
    private final ExerciseRepository exerciseRepository;

    @Transactional
    public CreateScheduledWorkoutResponse saveScheduledWorkout(CreateScheduledWorkoutRequest request) {
        ScheduledWorkout scheduledWorkout = CreateScheduledWorkoutRequest.toEntity(request);

        List<ScheduledWorkoutInfo> scheduledWorkoutInfos = request.getWorkoutInfos().stream()
                .map(infoRequest -> {
                    Exercise exercise = exerciseRepository.findByName(infoRequest.getExerciseName())
                            .orElseGet(() -> {
                                Exercise newExercise = new Exercise(
                                        infoRequest.getExerciseName(),
                                        "",
                                        "",
                                        infoRequest.getType()
                                );
                                return exerciseRepository.save(newExercise);
                            });
                    ScheduledWorkoutInfo info = WorkoutInfoRequest.toEntity(infoRequest, exercise);
                    info.setScheduledWorkout(scheduledWorkout);
                    return info;
                })
                .collect(Collectors.toList());

        List<ScheduledWorkoutRating> scheduledWorkoutRatings = request.getWorkoutRatings().stream()
                .map(ratingRequest -> {
                    ScheduledWorkoutRating rating = WorkoutRatingRequest.toEntity(ratingRequest);
                    rating.setScheduledWorkout(scheduledWorkout);
                    return rating;
                })
                .collect(Collectors.toList());

        scheduledWorkout.setScheduledWorkoutInfos(scheduledWorkoutInfos);
        scheduledWorkout.setScheduledWorkoutRatings(scheduledWorkoutRatings);

        ScheduledWorkout savedScheduledWorkout = scheduledWorkoutRepository.save(scheduledWorkout);

        return CreateScheduledWorkoutResponse.fromEntity(savedScheduledWorkout);
    }

    public List<GetScheduledWorkoutResponse> getScheduledWorkoutsByDate(LocalDate date) {
        LocalDateTime start = date.atTime(15, 0);
        LocalDateTime end = date.plusDays(1).atTime(14, 59, 59);

        List<ScheduledWorkout> scheduledWorkouts = scheduledWorkoutRepository.findByScheduledDateTimeBetween(start, end);
        return scheduledWorkouts.stream()
                .map(GetScheduledWorkoutResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
