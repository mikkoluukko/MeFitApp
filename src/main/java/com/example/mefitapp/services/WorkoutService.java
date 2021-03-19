package com.example.mefitapp.services;

import com.example.mefitapp.models.*;
import com.example.mefitapp.repositories.*;
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

    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private GoalRepository goalRepository;

    public List<ExerciseSet> getExerciseSetsByWorkout(Long id) {
        Workout returnWorkout = workoutRepository.findById(id).get();
        return exerciseSetRepository.findAllByWorkouts(returnWorkout);
    }

    public List<Profile> getProfilesByWorkout(Long id) {
        Workout returnWorkout = workoutRepository.findById(id).get();
        return profileRepository.findAllByWorkouts(returnWorkout);
    }

    public List<Program> getProgramsByWorkout(Long id) {
        Workout returnWorkout = workoutRepository.findById(id).get();
        return programRepository.findAllByWorkouts(returnWorkout);
    }

    public List<Goal> getGoalsByWorkout(Long id) {
        Workout returnWorkout = workoutRepository.findById(id).get();
        return goalRepository.findAllByWorkouts(returnWorkout);
    }
}