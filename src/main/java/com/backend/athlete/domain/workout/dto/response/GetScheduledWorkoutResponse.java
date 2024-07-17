package com.backend.athlete.domain.workout.dto.response;

import com.backend.athlete.domain.workout.domain.ScheduledWorkout;
import com.backend.athlete.domain.workout.domain.WorkoutInfo;
import com.backend.athlete.domain.workout.domain.WorkoutRating;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class GetScheduledWorkoutResponse {
    private Long id;
    private String title;
    private LocalDate date;
    private int rounds;
    private String time;
    private String notes;
    private List<WorkoutInfoResponse> workoutInfos;
    private Map<String, List<WorkoutRatingResponse>> workoutRatings;

    public GetScheduledWorkoutResponse(Long id, String title, LocalDate date, int rounds, String time, String notes,
                                       List<WorkoutInfoResponse> workoutInfos, Map<String, List<WorkoutRatingResponse>> workoutRatings) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.rounds = rounds;
        this.time = time;
        this.notes = notes;
        this.workoutInfos = workoutInfos;
        this.workoutRatings = workoutRatings;
    }

    public static GetScheduledWorkoutResponse fromEntity(ScheduledWorkout scheduledWorkout) {
        List<WorkoutInfoResponse> workoutInfos = scheduledWorkout.getWorkoutInfos().stream()
                .map(WorkoutInfoResponse::fromEntity)
                .collect(Collectors.toList());

        Map<String, List<WorkoutRatingResponse>> workoutRatings = scheduledWorkout.getWorkoutRatings().stream()
                .collect(Collectors.groupingBy(
                        WorkoutRating::getGender,
                        Collectors.mapping(WorkoutRatingResponse::fromEntity, Collectors.toList())
                ));

        return new GetScheduledWorkoutResponse(
                scheduledWorkout.getId(),
                scheduledWorkout.getTitle(),
                scheduledWorkout.getDate(),
                scheduledWorkout.getRounds(),
                scheduledWorkout.getTime(),
                scheduledWorkout.getNotes(),
                workoutInfos,
                workoutRatings
        );
    }
}
