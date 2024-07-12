package com.backend.athlete.domain.workout.dto.request;

import com.backend.athlete.domain.workout.domain.ScheduledWorkout;
import com.backend.athlete.domain.workout.domain.WorkoutInfo;
import com.backend.athlete.domain.workout.domain.WorkoutRating;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class CreateScheduledWorkoutRequest {
    private String title;
    private LocalDate date;
    private int rounds;
    private String time;
    private String notes;
    private List<WorkoutInfoRequest> workoutInfos;
    private List<WorkoutRatingRequest> workoutRatings;

    public static ScheduledWorkout toEntity(CreateScheduledWorkoutRequest request) {
        ScheduledWorkout scheduledWorkout = new ScheduledWorkout(
                request.getTitle(),
                request.getDate(),
                request.getRounds(),
                request.getTime(),
                request.getNotes()
        );

        List<WorkoutInfo> workoutInfos = request.getWorkoutInfos().stream()
                .map(WorkoutInfoRequest::toEntity)
                .collect(Collectors.toList());

        List<WorkoutRating> workoutRatings = request.getWorkoutRatings().stream()
                .map(WorkoutRatingRequest::toEntity)
                .collect(Collectors.toList());

        scheduledWorkout.setWorkoutInfos(workoutInfos);
        scheduledWorkout.setWorkoutRatings(workoutRatings);

        return scheduledWorkout;
    }
}
