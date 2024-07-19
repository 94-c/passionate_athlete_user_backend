package com.backend.athlete.domain.exercise.controller;

import com.backend.athlete.domain.exercise.application.ExerciseService;
import com.backend.athlete.domain.exercise.domain.type.ExerciseType;
import com.backend.athlete.domain.exercise.dto.response.GetExerciseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/exercises")
@RequiredArgsConstructor
public class ExerciseController {
    private final ExerciseService exerciseService;

    @GetMapping("/type/{type}")
    public ResponseEntity<List<GetExerciseResponse>> getExercisesByType(@PathVariable ExerciseType type) {
        List<GetExerciseResponse> exercises = exerciseService.getExercisesByType(type);
        return ResponseEntity.ok(exercises);
    }

}
