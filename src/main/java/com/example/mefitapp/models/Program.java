package com.example.mefitapp.models;

import com.fasterxml.jackson.annotation.JsonGetter;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Program {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String category;

    @ManyToMany
    @JoinTable(
            name = "program_workout",
            joinColumns = {@JoinColumn(name = "workout_id")},
            inverseJoinColumns = {@JoinColumn(name = "program_id")}
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

    @ManyToMany
    @JoinTable(
            name = "profile_program",
            joinColumns = {@JoinColumn(name = "profile_id")},
            inverseJoinColumns = {@JoinColumn(name = "program_id")}
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

    public Program() {
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Workout> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(List<Workout> workouts) {
        this.workouts = workouts;
    }

    public List<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }
}
