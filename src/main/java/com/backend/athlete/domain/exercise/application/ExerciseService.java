package com.backend.athlete.domain.exercise.application;

import com.backend.athlete.domain.exercise.domain.Exercise;
import com.backend.athlete.domain.exercise.domain.ExerciseRepository;
import com.backend.athlete.domain.exercise.domain.type.ExerciseType;
import com.backend.athlete.domain.exercise.dto.response.GetExerciseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;

    // 운동 타입별로 운동 종목을 가져오는 메소드
    public List<GetExerciseResponse> getExercisesByType(ExerciseType type) {
        List<Exercise> exercises = exerciseRepository.findByType(type);
        return exercises.stream()
                .map(GetExerciseResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
