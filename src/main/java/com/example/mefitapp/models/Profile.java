package com.example.mefitapp.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String first_name;

    @Column(nullable = false)
    private String last_name;

    @Column
    private long weight;

    @Column
    private long height;

    @Column
    private String image_link;

    @Column
    private String medical_conditions;

    @Column
    private String disabilities;

    @OneToOne
    @JoinColumn(name = "profile_id")
    private AppUser app_user;

    @JsonGetter("app_user")
    public String userGetter() {
        if(app_user != null) {
            return "/api/v1/users/" + app_user.getId();
        }
        return null;
    }

    @ManyToMany
    @JoinTable(
            name = "profile_workout",
            joinColumns = {@JoinColumn(name = "workout_id")},
            inverseJoinColumns = {@JoinColumn(name = "profile_id")}
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

    public Profile() {
    }

    public Profile(String first_name, String last_name, long weight, long Height, AppUser app_user) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.weight = weight;
        this.height = height;
        this.app_user = app_user;
    }

    public long getId() {
        return id;
    }

    public void setId(long profile_id) {
        this.id = profile_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }

    public String getImage_link() {
        return image_link;
    }

    public void setImage_link(String image_link) {
        this.image_link = image_link;
    }

    public String getMedical_conditions() {
        return medical_conditions;
    }

    public void setMedical_conditions(String medical_conditions) {
        this.medical_conditions = medical_conditions;
    }

    public String getDisabilities() {
        return disabilities;
    }

    public void setDisabilities(String disabilities) {
        this.disabilities = disabilities;
    }

    public AppUser getApp_user() {
        return app_user;
    }

    public void setApp_user(AppUser app_user) {
        this.app_user = app_user;
    }

    public List<Workout> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(List<Workout> workouts) {
        this.workouts = workouts;
    }
}