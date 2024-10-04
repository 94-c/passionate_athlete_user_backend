package com.backend.athlete.user.workout.dto.request;

import com.backend.athlete.user.workout.domain.ScheduledWorkout;
import com.backend.athlete.user.workout.domain.type.WorkoutMode;
import com.backend.athlete.user.workout.domain.type.WorkoutType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
public class CreateScheduledWorkoutRequest {
    private String title;
    private LocalDateTime date;
    private String time;
    private String notes;
    private WorkoutType workoutType;
    private WorkoutMode workoutMode;
    private List<WorkoutInfoRequest> workoutInfos;
    private List<WorkoutRatingRequest> workoutRatings;
    private Integer round;
    public static ScheduledWorkout toEntity(CreateScheduledWorkoutRequest request) {
        return new ScheduledWorkout(
                request.getTitle(),
                request.getDate(),
                request.getTime(),
                request.getNotes(),
                request.getWorkoutType(),
                request.getWorkoutMode(),
                request.getRound()
        );
    }
}


