package com.mefitapp.models;

import com.fasterxml.jackson.annotation.JsonGetter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String type;

    @Column(columnDefinition = "boolean default false")
    private Boolean is_complete;

    @ManyToMany
    @JoinTable(
            name = "exercise_set_workout",
            joinColumns = {@JoinColumn(name = "exercise_set_id")},
            inverseJoinColumns = {@JoinColumn(name = "workout_id")}
    )
    private Set<ExerciseSet> exerciseSets;

    @JsonGetter("exerciseSets")
    public List<String> exerciseSetsGetter() {
        if(exerciseSets != null) {
            return exerciseSets.stream()
                    .map(exerciseSet -> {
                        return "/api/v1/sets/" + exerciseSet.getId();
                    }).collect(Collectors.toList());
        }
        return null;
    }

    @ManyToMany
    @JoinTable(
            name = "goal_workout",
            joinColumns = {@JoinColumn(name = "goal_id")},
            inverseJoinColumns = {@JoinColumn(name = "workout_id")}
    )
    private Set<Goal> goals;

    @JsonGetter("goals")
    public List<String> goalsGetter() {
        if(goals != null) {
            return goals.stream()
                    .map(goal -> {
                        return "/api/v1/goals/" + goal.getId();
                    }).collect(Collectors.toList());
        }
        return null;
    }

    public Workout() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getIs_complete() {
        return is_complete;
    }

    public void setIs_complete(Boolean is_complete) {
        this.is_complete = is_complete;
    }

    public Set<ExerciseSet> getExerciseSets() {
        return exerciseSets;
    }

    public void setExerciseSets(Set<ExerciseSet> exerciseSets) {
        this.exerciseSets = exerciseSets;
    }

    public Set<Goal> getGoals() {
        return goals;
    }

    public void setGoals(Set<Goal> goals) {
        this.goals = goals;
    }
}
