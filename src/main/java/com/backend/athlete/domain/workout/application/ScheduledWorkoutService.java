package com.backend.athlete.domain.workout.application;

import com.backend.athlete.domain.workout.domain.*;
import com.backend.athlete.domain.workout.dto.request.CreateScheduledWorkoutRequest;
import com.backend.athlete.domain.workout.dto.request.WorkoutInfoRequest;
import com.backend.athlete.domain.workout.dto.request.WorkoutRatingRequest;
import com.backend.athlete.domain.workout.dto.response.CreateScheduledWorkoutResponse;
import com.backend.athlete.domain.workout.dto.response.GetScheduledWorkoutResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduledWorkoutService {
    private final ScheduledWorkoutRepository scheduledWorkoutRepository;

    public CreateScheduledWorkoutResponse saveScheduledWorkout(CreateScheduledWorkoutRequest request) {
        ScheduledWorkout scheduledWorkout = CreateScheduledWorkoutRequest.toEntity(request);

        List<WorkoutInfo> workoutInfos = request.getWorkoutInfos().stream()
                .map(infoRequest -> {
                    WorkoutInfo info = WorkoutInfoRequest.toEntity(infoRequest);
                    info.setScheduledWorkout(scheduledWorkout);
                    return info;
                })
                .collect(Collectors.toList());

        List<WorkoutRating> workoutRatings = request.getWorkoutRatings().stream()
                .map(ratingRequest -> {
                    WorkoutRating rating = WorkoutRatingRequest.toEntity(ratingRequest);
                    rating.setScheduledWorkout(scheduledWorkout);
                    return rating;
                })
                .collect(Collectors.toList());

        scheduledWorkout.setWorkoutInfos(workoutInfos);
        scheduledWorkout.setWorkoutRatings(workoutRatings);

        ScheduledWorkout savedScheduledWorkout = scheduledWorkoutRepository.save(scheduledWorkout);

        return CreateScheduledWorkoutResponse.fromEntity(savedScheduledWorkout);
    }

    public List<GetScheduledWorkoutResponse> getScheduledWorkoutsByDate(LocalDate date) {
        List<ScheduledWorkout> scheduledWorkouts = scheduledWorkoutRepository.findByDate(date);
        return scheduledWorkouts.stream()
                .map(GetScheduledWorkoutResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
