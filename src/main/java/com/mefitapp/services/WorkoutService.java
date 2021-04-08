package com.mefitapp.services;


import com.mefitapp.models.*;
import com.mefitapp.repositories.*;
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
    private GoalRepository goalRepository;

    public List<ExerciseSet> getExerciseSetsByWorkout(Long id) {
        Workout returnWorkout = workoutRepository.findById(id).get();
        return exerciseSetRepository.findAllByWorkouts(returnWorkout);
    }

    public List<Goal> getGoalsByWorkout(Long id) {
        Workout returnWorkout = workoutRepository.findById(id).get();
        return goalRepository.findAllByWorkouts(returnWorkout);
    }

    public void updateList(Object listName, Long workoutId, String itemId) {
        switch (listName.toString()) {
            case "exerciseSets" -> updateExerciseSets(workoutId, Long.valueOf(itemId));
            case "goals" -> updateGoals(workoutId, Long.valueOf(itemId));
        }
    }

    public void updateExerciseSets(Long workoutId, Long exerciseSetId) {
        Workout toBePatchedWorkout = workoutRepository.findById(workoutId).get();
        ExerciseSet toBeAddedExerciseSet = exerciseSetRepository.findById(exerciseSetId).get();
        toBePatchedWorkout.getExerciseSets().add(toBeAddedExerciseSet);
        workoutRepository.save(toBePatchedWorkout);
    }

    public void updateGoals(Long workoutId, Long goalId) {
        Workout toBePatchedWorkout = workoutRepository.findById(workoutId).get();
        Goal toBeAddedGoal = goalRepository.findById(goalId).get();
        toBePatchedWorkout.getGoals().add(toBeAddedGoal);
        workoutRepository.save(toBePatchedWorkout);
    }
}