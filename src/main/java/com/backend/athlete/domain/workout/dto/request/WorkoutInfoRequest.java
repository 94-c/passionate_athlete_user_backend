package com.backend.athlete.domain.workout.dto.request;

import com.backend.athlete.domain.exercise.domain.Exercise;
import com.backend.athlete.domain.exercise.domain.type.ExerciseType;
import com.backend.athlete.domain.workout.domain.ScheduledWorkoutInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkoutInfoRequest {
    private String exerciseName;
    private String info;
    private ExerciseType type;
    public static ScheduledWorkoutInfo toEntity(WorkoutInfoRequest request, Exercise exercise) {
        ScheduledWorkoutInfo scheduledWorkoutInfo = new ScheduledWorkoutInfo(exercise, request.getInfo());
        scheduledWorkoutInfo.setExercise(exercise);
        return scheduledWorkoutInfo;
    }
}

