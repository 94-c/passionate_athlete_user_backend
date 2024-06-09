package com.backend.athlete.application;

import com.backend.athlete.domain.execise.*;
import com.backend.athlete.presentation.exercise.request.CreateWorkoutInfoRequest;
import com.backend.athlete.presentation.exercise.request.CreateWorkoutRequest;
import com.backend.athlete.presentation.exercise.response.CreateWorkoutResponse;
import com.backend.athlete.support.exception.ServiceException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorkoutService {
    private final WorkoutRepository workoutRepository;
    private final ExerciseRepository exerciseRepository;
    public WorkoutService(WorkoutRepository workoutRepository, ExerciseRepository exerciseRepository) {
        this.workoutRepository = workoutRepository;
        this.exerciseRepository = exerciseRepository;
    }

    public CreateWorkoutResponse createWorkout(CreateWorkoutRequest request) {
        duplicateWorkoutTitle(request.getTitle());

        Workout workout = CreateWorkoutRequest.toEntity(request);

        if (request.getWorkoutInfos() != null) {
            List<WorkoutInfo> workoutInfos = request.getWorkoutInfos().stream()
                    .map(infoRequest -> {
                        Exercise exercise = exerciseRepository.findById(infoRequest.getExerciseId())
                                .orElseThrow(() -> new RuntimeException("Exercise not found"));
                        return CreateWorkoutInfoRequest.toEntity(infoRequest, workout, exercise);
                    })
                    .collect(Collectors.toList());

            workoutInfos.forEach(workout::addWorkoutInfo);
        }

        Workout savedWorkout = workoutRepository.save(workout);
        return CreateWorkoutResponse.fromEntity(savedWorkout);
    }


    public List<Workout> getAllWorkouts() {
        return workoutRepository.findAll();
    }

    public Workout getWorkoutById(Long id) {
        return workoutRepository.findById(id).orElse(null);
    }

    public void deleteWorkout(Long id) {
        workoutRepository.deleteById(id);
    }

    protected void duplicateWorkoutTitle(String title) {
        Optional<Workout> workout = workoutRepository.findByTitle(title);
        if (workout.isPresent()) {
            throw new ServiceException("이미 등록 된 오늘의 운동명입니다.");
        }
    }
}
