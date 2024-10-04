package com.backend.athlete.user.exercise.controller;

import com.backend.athlete.user.exercise.application.ExerciseService;
import com.backend.athlete.user.exercise.domain.type.ExerciseType;
import com.backend.athlete.user.exercise.dto.request.CreateExerciseRequest;
import com.backend.athlete.user.exercise.dto.response.CreateExerciseResponse;
import com.backend.athlete.user.exercise.dto.response.GetExerciseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<CreateExerciseResponse> createExercise(@RequestBody CreateExerciseRequest request) {
        CreateExerciseResponse response = exerciseService.saveExercise(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
