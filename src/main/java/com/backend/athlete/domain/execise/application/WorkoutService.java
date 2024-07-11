package com.backend.athlete.domain.execise.application;

import com.backend.athlete.domain.execise.domain.*;
import com.backend.athlete.domain.execise.domain.type.LevelType;
import com.backend.athlete.domain.execise.dto.request.CreateWorkoutInfoRequest;
import com.backend.athlete.domain.execise.dto.request.CreateWorkoutRequest;
import com.backend.athlete.domain.execise.dto.response.GetWorkoutResponse;
import com.backend.athlete.support.common.response.PagedResponse;
import com.backend.athlete.support.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public PagedResponse<GetWorkoutResponse> getAllWorkoutsPaged(String title, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Workout> workouts = workoutRepository.findAllWithDetails(title, pageable);
        return PagedResponse.fromPage(workouts.map(GetWorkoutResponse::fromEntity));
    }

    public GetWorkoutResponse createWorkout(CreateWorkoutRequest request) {
        duplicateWorkoutTitle(request.getTitle());

        Workout workout = CreateWorkoutRequest.toEntity(request);

        if (request.getWorkoutInfos() != null) {
            List<WorkoutInfo> workoutInfos = request.getWorkoutInfos().stream()
                    .map(infoRequest -> {
                        Exercise exercise = exerciseRepository.findById(infoRequest.getExerciseId())
                                .orElseThrow(() -> new NotFoundException("운동 종목을 찾지 못했습니다.", HttpStatus.NOT_FOUND));
                        return CreateWorkoutInfoRequest.toEntity(infoRequest, workout, exercise);
                    })
                    .collect(Collectors.toList());

            workoutInfos.forEach(workout::addWorkoutInfo);
        }

        Workout savedWorkout = workoutRepository.save(workout);
        return GetWorkoutResponse.fromEntity(savedWorkout);
    }

    public GetWorkoutResponse getWorkoutById(Long id) {
        Workout workout = workoutRepository.findWorkoutWithDetailsById(id);
        return GetWorkoutResponse.fromEntity(workout);
    }

    public GetWorkoutResponse updateWorkout(Long id, CreateWorkoutRequest request) {
        Workout workout = workoutRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Workout not found"));

        workout.update(
                request.getTitle(),
                request.getDescription(),
                request.getRound(),
                request.getTime()
        );

        if (request.getWorkoutInfos() != null) {
            List<WorkoutInfo> workoutInfos = request.getWorkoutInfos().stream()
                    .map(infoRequest -> convertToWorkoutInfo(infoRequest, workout))
                    .collect(Collectors.toList());

            workout.updateWorkoutInfos(workoutInfos);
        }

        Workout savedWorkout = workoutRepository.save(workout);
        return GetWorkoutResponse.fromEntity(savedWorkout);
    }

    protected void duplicateWorkoutTitle(String title) {
        Optional<Workout> workout = workoutRepository.findByTitle(title);
        if (workout.isPresent()) {
            throw new NotFoundException("이미 등록 된 오늘의 운동명입니다.", HttpStatus.NOT_FOUND);
        }
    }

    protected WorkoutInfo convertToWorkoutInfo(CreateWorkoutInfoRequest infoRequest, Workout workout) {
        Exercise exercise = exerciseRepository.findById(infoRequest.getExerciseId())
                .orElseThrow(() -> new RuntimeException("Exercise not found"));
        List<WorkoutLevel> levels = infoRequest.getLevels().stream()
                .map(levelRequest -> new WorkoutLevel(
                        LevelType.valueOf(levelRequest.getLevel()),
                        null,
                        levelRequest.getMaleWeight(),
                        levelRequest.getFemaleWeight(),
                        levelRequest.getMaleCount(),
                        levelRequest.getFemaleCount()))
                .collect(Collectors.toList());
        WorkoutInfo workoutInfo = new WorkoutInfo(workout, exercise);
        levels.forEach(level -> level.setWorkoutInfo(workoutInfo));
        workoutInfo.setLevels(levels);
        return workoutInfo;
    }

    @Transactional
    public void deleteWorkout(Long id) {
        Workout workout = workoutRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("오늘의 운동을 찾지 못했습니다.", HttpStatus.NOT_FOUND));

        workoutRepository.delete(workout);
    }
}
