package com.backend.athlete.domain.workout.dto.response;

import com.backend.athlete.domain.workout.domain.ScheduledWorkout;
import com.backend.athlete.domain.workout.domain.type.WorkoutType;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CreateScheduledWorkoutResponse {
    private Long id;
    private String title;
    private LocalDate date;
    private int rounds;
    private String time;
    private String notes;
    private WorkoutType workoutType;
    private List<WorkoutInfoResponse> workoutInfos;
    private List<WorkoutRatingResponse> workoutRatings;

    public CreateScheduledWorkoutResponse(Long id, String title, LocalDate date, int rounds, String time, String notes, WorkoutType workoutType,
                                          List<WorkoutInfoResponse> workoutInfos, List<WorkoutRatingResponse> workoutRatings) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.rounds = rounds;
        this.time = time;
        this.notes = notes;
        this.workoutType = workoutType;
        this.workoutInfos = workoutInfos;
        this.workoutRatings = workoutRatings;
    }

    public static CreateScheduledWorkoutResponse fromEntity(ScheduledWorkout scheduledWorkout) {
        List<WorkoutInfoResponse> workoutInfos = scheduledWorkout.getWorkoutInfos().stream()
                .map(WorkoutInfoResponse::fromEntity)
                .collect(Collectors.toList());

        List<WorkoutRatingResponse> workoutRatings = scheduledWorkout.getWorkoutRatings().stream()
                .map(WorkoutRatingResponse::fromEntity)
                .collect(Collectors.toList());

        return new CreateScheduledWorkoutResponse(
                scheduledWorkout.getId(),
                scheduledWorkout.getTitle(),
                scheduledWorkout.getDate(),
                scheduledWorkout.getRounds(),
                scheduledWorkout.getTime(),
                scheduledWorkout.getNotes(),
                scheduledWorkout.getWorkoutType(),
                workoutInfos,
                workoutRatings
        );
    }
}




