package com.backend.athlete.domain.execise.application;

import com.backend.athlete.domain.execise.domain.Exercise;
import com.backend.athlete.domain.execise.domain.ExerciseRepository;
import com.backend.athlete.domain.execise.dto.request.CreateExerciseRequest;
import com.backend.athlete.domain.execise.dto.request.UpdateExerciseRequest;
import com.backend.athlete.domain.execise.dto.response.GetExerciseResponse;
import com.backend.athlete.support.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;
    public ExerciseService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }
    public GetExerciseResponse createExercise(CreateExerciseRequest request) {
        duplicateExercise(request.getName());

        Exercise newExercise = exerciseRepository.save(CreateExerciseRequest.toEntity(request));

        return GetExerciseResponse.fromEntity(newExercise);
    }

    public GetExerciseResponse getExercise(Long id) {
        return GetExerciseResponse.fromEntity(exerciseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재 하지 않는 운동입니다.", HttpStatus.NOT_FOUND)));
    }

    public GetExerciseResponse updateExercise(Long id, UpdateExerciseRequest request) {
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재 하지 않는 운동입니다.", HttpStatus.NOT_FOUND));

        duplicateExercise(request.getName());

        exercise.update(
                request.getName(),
                request.getDescription(),
                request.getLink()
        );

        Exercise updateExercise = exerciseRepository.save(exercise);
        return GetExerciseResponse.fromEntity(updateExercise);
    }

    protected void duplicateExercise(String name) {
        Optional<Exercise> exercise = exerciseRepository.findByName(name);
        if (exercise.isPresent()) {
            throw new NotFoundException("중복 되는 운동이 존재합니다.", HttpStatus.NOT_FOUND);
        }
    }


}
