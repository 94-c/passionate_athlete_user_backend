package com.backend.athlete.domain.exercise.application;

import com.backend.athlete.domain.exercise.domain.Exercise;
import com.backend.athlete.domain.exercise.domain.ExerciseRepository;
import com.backend.athlete.domain.exercise.domain.type.ExerciseType;
import com.backend.athlete.domain.exercise.dto.request.CreateExerciseRequest;
import com.backend.athlete.domain.exercise.dto.response.CreateExerciseResponse;
import com.backend.athlete.domain.exercise.dto.response.GetExerciseResponse;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;

    public List<GetExerciseResponse> getExercisesByType(ExerciseType type) {
        List<Exercise> exercises = exerciseRepository.findByType(type);
        return exercises.stream()
                .map(GetExerciseResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public CreateExerciseResponse saveExercise(CreateExerciseRequest request) {
        if (exerciseRepository.findByName(request.getName()).isPresent()) {
            throw new ServiceException("동일한 이름의 운동이 존재합니다.");
        }

        Exercise saveExercise = exerciseRepository.save(CreateExerciseRequest.toEntity(request));
        return CreateExerciseResponse.fromEntity(saveExercise);
    }

}
