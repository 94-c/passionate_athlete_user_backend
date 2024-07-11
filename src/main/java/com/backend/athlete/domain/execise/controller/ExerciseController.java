package com.backend.athlete.domain.execise.controller;

import com.backend.athlete.domain.execise.application.ExerciseService;
import com.backend.athlete.domain.execise.dto.request.CreateExerciseRequest;
import com.backend.athlete.domain.execise.dto.request.UpdateExerciseRequest;
import com.backend.athlete.domain.execise.dto.response.GetExerciseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/exercises")
public class ExerciseController {
    private final ExerciseService exerciseService;
    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('MANAGER') or hasAnyAuthority('ADMIN')")
    public ResponseEntity<GetExerciseResponse> createExercise(@RequestBody CreateExerciseRequest request) {
        GetExerciseResponse response = exerciseService.createExercise(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetExerciseResponse> getExercise(@PathVariable Long id) {
        GetExerciseResponse response = exerciseService.getExercise(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetExerciseResponse> updateExercise(@PathVariable Long id,
                                                              @RequestBody UpdateExerciseRequest request) {
        GetExerciseResponse response = exerciseService.updateExercise(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
