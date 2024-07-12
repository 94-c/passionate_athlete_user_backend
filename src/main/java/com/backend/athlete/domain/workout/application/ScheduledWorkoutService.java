package com.backend.athlete.domain.workout.application;

import com.backend.athlete.domain.workout.domain.*;
import com.backend.athlete.domain.workout.dto.request.CreateScheduledWorkoutRequest;
import com.backend.athlete.domain.workout.dto.request.WorkoutInfoRequest;
import com.backend.athlete.domain.workout.dto.request.WorkoutRatingRequest;
import com.backend.athlete.domain.workout.dto.response.CreateScheduledWorkoutResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduledWorkoutService {
    private final ScheduledWorkoutRepository scheduledWorkoutRepository;
    private final WorkoutInfoRepository workoutInfoRepository;
    private final WorkoutRatingRepository workoutRatingRepository;

    public CreateScheduledWorkoutResponse saveScheduledWorkout(CreateScheduledWorkoutRequest request) {
        ScheduledWorkout scheduledWorkout = CreateScheduledWorkoutRequest.toEntity(request);

        ScheduledWorkout savedScheduledWorkout = scheduledWorkoutRepository.save(scheduledWorkout);

        List<WorkoutInfo> workoutInfos = request.getWorkoutInfos().stream()
                .map(infoRequest -> {
                    WorkoutInfo info = WorkoutInfoRequest.toEntity(infoRequest);
                    info.setScheduledWorkout(savedScheduledWorkout);
                    return workoutInfoRepository.save(info);
                })
                .collect(Collectors.toList());

        List<WorkoutRating> workoutRatings = request.getWorkoutRatings().stream()
                .map(ratingRequest -> {
                    WorkoutRating rating = WorkoutRatingRequest.toEntity(ratingRequest);
                    rating.setScheduledWorkout(savedScheduledWorkout);
                    return workoutRatingRepository.save(rating);
                })
                .collect(Collectors.toList());

        savedScheduledWorkout.setWorkoutInfos(workoutInfos);
        savedScheduledWorkout.setWorkoutRatings(workoutRatings);

        return CreateScheduledWorkoutResponse.fromEntity(savedScheduledWorkout);
    }

}
