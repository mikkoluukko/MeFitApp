package com.example.mefitapp.services;

import com.example.mefitapp.models.ExerciseSet;
import com.example.mefitapp.models.Workout;
import com.example.mefitapp.repositories.ExerciseSetRepository;
import com.example.mefitapp.repositories.ProfileRepository;
import com.example.mefitapp.repositories.WorkoutRepository;
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
