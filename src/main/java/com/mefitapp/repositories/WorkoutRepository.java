package com.mefitapp.repositories;

import com.mefitapp.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    List<Workout> findAllByExerciseSets(ExerciseSet exerciseSet);
    List<Workout> findAllByGoals(Goal goal);
}
