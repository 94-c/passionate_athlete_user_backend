package com.backend.athlete.domain.workout.dto.request;

import com.backend.athlete.domain.user.domain.User;
import com.backend.athlete.domain.workout.domain.ScheduledWorkout;
import com.backend.athlete.domain.workout.domain.WorkoutRecord;
import com.backend.athlete.domain.workout.domain.type.WorkoutRecordType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter @Setter
public class CreateWorkoutRecordRequest {
    @NotNull(message = "운동 타입을 입력 해주세요.")
    private WorkoutRecordType exerciseType;

    private Long scheduledWorkoutId;
    private String exerciseName;
    private Integer repetitions;
    private String duration;
    private String rating;
    private Boolean success;
    private String recordContent;

    public CreateWorkoutRecordRequest(WorkoutRecordType exerciseType, Long scheduledWorkoutId, String exerciseName, Integer repetitions, String duration, String rating, Boolean success) {
        this.exerciseType = exerciseType;
        this.scheduledWorkoutId = scheduledWorkoutId;
        this.exerciseName = exerciseName;
        this.repetitions = repetitions;
        this.duration = duration;
        this.rating = rating;
        this.success = success;
    }

    public static WorkoutRecord toEntity(CreateWorkoutRecordRequest request, User user, ScheduledWorkout scheduledWorkout) {
        return new WorkoutRecord(
                user,
                request.getExerciseType(),
                scheduledWorkout,
                request.getExerciseName(),
                request.getRepetitions(),
                request.getDuration(),
                request.getRating(),
                request.getSuccess(),
                request.getRecordContent()
        );
    }
}
