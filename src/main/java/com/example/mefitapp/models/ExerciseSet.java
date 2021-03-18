package com.example.mefitapp.models;

import com.fasterxml.jackson.annotation.JsonGetter;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

// Use ExerciseSet instead of Set as Set is a reserved word
@Entity
public class ExerciseSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private long exercise_repetitions;

    @ManyToOne
    private Exercise exercise;

    @JsonGetter("exercise")
    public String exerciseGetter() {
        if (exercise != null) {
            return "/api/v1/exercise/" + exercise.getId();
        } else {
            return null;
        }
    }

    @ManyToMany
    @JoinTable(
            name = "exercise_set_workout",
            joinColumns = {@JoinColumn(name = "workout_id")},
            inverseJoinColumns = {@JoinColumn(name = "exercise_set_id")}
    )
    private List<Workout> workouts;

    @JsonGetter("workouts")
    public List<String> workoutsGetter() {
        if(workouts != null) {
            return workouts.stream()
                    .map(workout -> {
                        return "/api/v1/workout/" + workout.getId();
                    }).collect(Collectors.toList());
        }
        return null;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getExercise_repetitions() {
        return exercise_repetitions;
    }

    public void setExercise_repetitions(long excerise_repetitions) {
        this.exercise_repetitions = excerise_repetitions;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }
}
