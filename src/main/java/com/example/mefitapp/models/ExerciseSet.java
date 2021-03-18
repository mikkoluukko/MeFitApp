package com.example.mefitapp.models;

import com.fasterxml.jackson.annotation.JsonGetter;

import javax.persistence.*;

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
