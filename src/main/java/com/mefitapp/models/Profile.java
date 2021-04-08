package com.mefitapp.models;

import com.fasterxml.jackson.annotation.JsonGetter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Profile {
    @Id
    private String id;

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

    @OneToMany
    @JoinColumn(name = "goal_id")
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

    public Profile() {
    }

    public String getId() {
        return id;
    }

    public void setId(String profile_id) {
        this.id = profile_id;
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

    public Set<Goal> getGoals() {
        return goals;
    }

    public void setGoals(Set<Goal> goals) {
        this.goals = goals;
    }
}