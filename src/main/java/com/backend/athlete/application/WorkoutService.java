package com.backend.athlete.application;

import com.backend.athlete.domain.execise.Workout;
import com.backend.athlete.domain.execise.WorkoutRepository;
import com.backend.athlete.presentation.exercise.request.CreateWorkoutRequest;
import com.backend.athlete.presentation.exercise.response.CreateWorkoutResponse;
import com.backend.athlete.support.exception.ServiceException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WorkoutService {
    private final WorkoutRepository workoutRepository;

    public WorkoutService(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    public CreateWorkoutResponse createWorkout(CreateWorkoutRequest request) {
        duplicateWorkoutTitle(request.getTitle());

        Workout workout = workoutRepository.save(CreateWorkoutRequest.toEntity(request));

        return CreateWorkoutResponse.toEntity(workout);
    }

    protected void duplicateWorkoutTitle(String title) {
        Optional<Workout> workout = workoutRepository.findByTitle(title);
        if (workout.isPresent()) {
            throw new ServiceException("이미 등록 된 오늘의 운동명입니다.");
        }
    }
}
