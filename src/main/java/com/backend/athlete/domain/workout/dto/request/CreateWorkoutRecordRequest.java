package com.backend.athlete.domain.workout.dto.request;

import com.backend.athlete.domain.user.domain.User;
import com.backend.athlete.domain.workout.domain.ScheduledWorkout;
import com.backend.athlete.domain.workout.domain.WorkoutRecord;
import com.backend.athlete.domain.workout.domain.WorkoutRecordHistory;
import com.backend.athlete.domain.workout.domain.type.WorkoutRecordType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Getter @Setter
public class CreateWorkoutRecordRequest {
    @NotNull(message = "운동 타입을 입력 해주세요.")
    private WorkoutRecordType exerciseType;

    private Long scheduledWorkoutId;
    private Integer rounds;
    private String duration;
    private String rating;
    private Boolean success;
    private String recordContent;
    private List<WorkoutHistoryRequest> workoutDetails;
    private Boolean isShared;
    public static WorkoutRecord toEntity(CreateWorkoutRecordRequest request, User user, ScheduledWorkout scheduledWorkout) {
        return new WorkoutRecord(
                user,
                request.getExerciseType(),
                scheduledWorkout,
                request.getRounds(),
                request.getDuration(),
                request.getRating(),
                request.getSuccess(),
                request.getRecordContent(),
                false
        );
    }
}
