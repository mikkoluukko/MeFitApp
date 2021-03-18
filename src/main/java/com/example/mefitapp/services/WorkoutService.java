package com.example.mefitapp.services;

import com.example.mefitapp.models.Exercise;
import com.example.mefitapp.models.ExerciseSet;
import com.example.mefitapp.models.Profile;
import com.example.mefitapp.models.Workout;
import com.example.mefitapp.repositories.ExerciseRepository;
import com.example.mefitapp.repositories.ExerciseSetRepository;
import com.example.mefitapp.repositories.ProfileRepository;
import com.example.mefitapp.repositories.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutService {
    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private ExerciseSetRepository exerciseSetRepository;

    @Autowired
    private ProfileRepository profileRepository;

    public List<ExerciseSet> getExerciseSetsByWorkout(Long id) {
        Workout returnWorkout = workoutRepository.findById(id).get();
        return exerciseSetRepository.findAllByWorkouts(returnWorkout);
    }

    public List<Profile> getProfilesByWorkout(Long id) {
        Workout returnWorkout = workoutRepository.findById(id).get();
        return profileRepository.findAllByWorkouts(returnWorkout);
    }
}