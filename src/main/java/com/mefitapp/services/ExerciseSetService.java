package com.mefitapp.services;

import com.mefitapp.models.ExerciseSet;
import com.mefitapp.models.Workout;
import com.mefitapp.repositories.ExerciseSetRepository;
import com.mefitapp.repositories.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseSetService {
    @Autowired
    private ExerciseSetRepository exerciseSetRepository;

    @Autowired
    private WorkoutRepository workoutRepository;

    public List<Workout> getWorkoutsByExerciseSet(Long id) {
        ExerciseSet returnExerciseSet = exerciseSetRepository.findById(id).get();
        return workoutRepository.findAllByExerciseSets(returnExerciseSet);
    }
}
