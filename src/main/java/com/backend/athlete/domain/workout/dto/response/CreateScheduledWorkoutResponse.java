package com.backend.athlete.domain.workout.dto.response;

import com.backend.athlete.domain.workout.domain.ScheduledWorkout;
import com.backend.athlete.domain.workout.domain.type.WorkoutMode;
import com.backend.athlete.domain.workout.domain.type.WorkoutType;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CreateScheduledWorkoutResponse {
    private Long id;
    private String title;
    private LocalDateTime date;
    private String time;
    private String notes;
    private WorkoutType workoutType;
    private WorkoutMode workoutMode;
    private List<WorkoutInfoResponse> workoutInfos;
    private List<WorkoutRatingResponse> workoutRatings;
    private Integer round;

    public CreateScheduledWorkoutResponse(Long id, String title, LocalDateTime date, String time, String notes, WorkoutType workoutType, WorkoutMode workoutMode,
                                          List<WorkoutInfoResponse> workoutInfos, List<WorkoutRatingResponse> workoutRatings, Integer round) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.time = time;
        this.notes = notes;
        this.workoutType = workoutType;
        this.workoutMode = workoutMode;
        this.workoutInfos = workoutInfos;
        this.workoutRatings = workoutRatings;
        this.round = round;
    }

    public static CreateScheduledWorkoutResponse fromEntity(ScheduledWorkout scheduledWorkout) {
        List<WorkoutInfoResponse> workoutInfos = scheduledWorkout.getScheduledWorkoutInfos().stream()
                .map(WorkoutInfoResponse::fromEntity)
                .collect(Collectors.toList());

        List<WorkoutRatingResponse> workoutRatings = scheduledWorkout.getScheduledWorkoutRatings().stream()
                .map(WorkoutRatingResponse::fromEntity)
                .collect(Collectors.toList());

        return new CreateScheduledWorkoutResponse(
                scheduledWorkout.getId(),
                scheduledWorkout.getTitle(),
                scheduledWorkout.getScheduledDateTime(),
                scheduledWorkout.getTime(),
                scheduledWorkout.getNotes(),
                scheduledWorkout.getWorkoutType(),
                scheduledWorkout.getWorkoutMode(),
                workoutInfos,
                workoutRatings,
                scheduledWorkout.getRound()
        );
    }
}






