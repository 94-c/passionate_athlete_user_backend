package com.backend.athlete.presentation.exercise.request;

import com.backend.athlete.domain.execise.Workout;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.sql.Time;
import java.util.List;

@Getter @Setter
public class CreateWorkoutRequest {

    @NotNull(message = "오늘의 운동명을 입력해주세요.")
    private String title;
    private String description;
    private String round;
    private Time time;
    private List<CreateWorkoutInfoRequest> workoutInfos;

    public CreateWorkoutRequest(String title, String description, String round, Time time, List<CreateWorkoutInfoRequest> workoutInfos) {
        this.title = title;
        this.description = description;
        this.round = round;
        this.time = time;
        this.workoutInfos = workoutInfos;
    }

    public static Workout toEntity(CreateWorkoutRequest request) {
        return new Workout(
                request.title,
                request.description,
                request.round,
                request.time
        );
    }
}
