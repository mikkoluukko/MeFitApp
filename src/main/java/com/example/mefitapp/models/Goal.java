package com.example.mefitapp.models;

import com.fasterxml.jackson.annotation.JsonGetter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private Date end_date;

    @Column(columnDefinition = "boolean default false")
    private Boolean is_achieved;

    @ManyToMany
    @JoinTable(
            name = "goal_workout",
            joinColumns = {@JoinColumn(name = "workout_id")},
            inverseJoinColumns = {@JoinColumn(name = "goal_id")}
    )
    private Set<Workout> workouts;

    @JsonGetter("workouts")
    public List<String> workoutsGetter() {
        if(workouts != null) {
            return workouts.stream()
                    .map(workout -> {
                        return "/api/v1/workouts/" + workout.getId();
                    }).collect(Collectors.toList());
        }
        return null;
    }

    @ManyToOne
    private Profile profile;

    @JsonGetter("profile")
    public String profileGetter() {
        if (profile != null) {
            return "/api/v1/profiles/" + profile.getId();
        } else {
            return null;
        }
    }

    @ManyToMany
    @JoinTable(
            name = "program_goal",
            joinColumns = {@JoinColumn(name = "program_id")},
            inverseJoinColumns = {@JoinColumn(name = "goal_id")}
    )
    private Set<Program> programs;

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

    public Goal() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public Boolean getIs_achieved() {
        return is_achieved;
    }

    public void setIs_achieved(Boolean is_achieved) {
        this.is_achieved = is_achieved;
    }

    public Set<Workout> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(Set<Workout> workouts) {
        this.workouts = workouts;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Set<Program> getPrograms() {
        return programs;
    }

    public void setPrograms(Set<Program> programs) {
        this.programs = programs;
    }
}
