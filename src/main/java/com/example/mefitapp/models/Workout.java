package com.example.mefitapp.models;

import com.example.mefitapp.models.ExerciseSet;
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
    private List<ExerciseSet> exerciseSets;

    @JsonGetter("exerciseSets")
    public List<String> exerciseSetsGetter() {
        if(exerciseSets != null) {
            return exerciseSets.stream()
                    .map(exerciseSet -> {
                        return "/api/v1/set/" + exerciseSet.getId();
                    }).collect(Collectors.toList());
        }
        return null;
    }

    @ManyToMany
    @JoinTable(
            name = "profile_workout",
            joinColumns = {@JoinColumn(name = "profile_id")},
            inverseJoinColumns = {@JoinColumn(name = "workout_id")}
    )
    private List<Profile> profiles;

    @JsonGetter("profiles")
    public List<String> profilesGetter() {
        if(profiles != null) {
            return profiles.stream()
                    .map(profile -> {
                        return "/api/v1/profile/" + profile.getId();
                    }).collect(Collectors.toList());
        }
        return null;
    }

    @ManyToMany
    @JoinTable(
            name = "program_workout",
            joinColumns = {@JoinColumn(name = "program_id")},
            inverseJoinColumns = {@JoinColumn(name = "workout_id")}
    )
    private List<Program> programs;

    @JsonGetter("programs")
    public List<String> programsGetter() {
        if(programs != null) {
            return programs.stream()
                    .map(program -> {
                        return "/api/v1/program/" + program.getId();
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

    public List<ExerciseSet> getExerciseSets() {
        return exerciseSets;
    }

    public void setExerciseSets(List<ExerciseSet> exerciseSets) {
        this.exerciseSets = exerciseSets;
    }
}
