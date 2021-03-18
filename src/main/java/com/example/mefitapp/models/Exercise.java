package com.example.mefitapp.models;

import com.fasterxml.jackson.annotation.JsonGetter;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column
    private String target_muscle_group;

    @Column
    private String image_link;

    @Column
    private String vid_link;

    @OneToMany
    @JoinColumn(name = "exercise_id")
    List<ExerciseSet> exerciseSets;

    @JsonGetter("exerciseSets")
    public List<String> exerciseSetsGetter() {
        if (exerciseSets != null) {
            return exerciseSets.stream()
                    .map(exerciseSet -> {
                        return "/api/v1/set/" + exerciseSet.getId();
                    }).collect(Collectors.toList());
        }
        return null;
    }

    public Exercise() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTarget_muscle_group() {
        return target_muscle_group;
    }

    public void setTarget_muscle_group(String target_muscle_group) {
        this.target_muscle_group = target_muscle_group;
    }

    public String getImage_link() {
        return image_link;
    }

    public void setImage_link(String image_link) {
        this.image_link = image_link;
    }

    public String getVid_link() {
        return vid_link;
    }

    public void setVid_link(String vid_link) {
        this.vid_link = vid_link;
    }

    public List<ExerciseSet> getExerciseSets() {
        return exerciseSets;
    }

    public void setExerciseSets(List<ExerciseSet> exerciseSets) {
        this.exerciseSets = exerciseSets;
    }
}