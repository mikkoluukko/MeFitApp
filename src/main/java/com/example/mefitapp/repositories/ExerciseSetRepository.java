package com.example.mefitapp.repositories;

import com.example.mefitapp.models.Exercise;
import com.example.mefitapp.models.ExerciseSet;
import com.example.mefitapp.models.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseSetRepository extends JpaRepository<ExerciseSet, Long> {
    List<ExerciseSet> findAllByExercise(Exercise exercise);
    List<ExerciseSet> findAllByWorkouts(Workout workout);
}
