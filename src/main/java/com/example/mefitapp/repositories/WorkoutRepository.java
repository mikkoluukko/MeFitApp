package com.example.mefitapp.repositories;


import com.example.mefitapp.models.ExerciseSet;
import com.example.mefitapp.models.Profile;
import com.example.mefitapp.models.Program;
import com.example.mefitapp.models.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    List<Workout> findAllByExerciseSets(ExerciseSet exerciseSet);
    List<Workout> findAllByProfiles(Profile profile);
    List<Workout> findAllByPrograms(Program program);
}
