package com.mefitapp.services;

import com.mefitapp.models.Goal;
import com.mefitapp.models.Profile;
import com.mefitapp.models.Workout;
import com.mefitapp.repositories.GoalRepository;
import com.mefitapp.repositories.ProfileRepository;
import com.mefitapp.repositories.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoalService {

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private ProfileRepository profileRepository;

    public List<Workout> getWorkoutsByGoal(Long id) {
        Goal returnGoal = goalRepository.findById(id).get();
        return workoutRepository.findAllByGoals(returnGoal);
    }

    public void updateList(Object listName, Long goalId, String itemId) {
        switch (listName.toString()) {
            case "workouts" -> updateExerciseSets(goalId, Long.valueOf(itemId));
            case "profile" -> updateProfile(goalId, itemId);
        }
    }

    public void updateExerciseSets(Long goalId, Long workoutId) {
        Goal toBePatchedGoal = goalRepository.findById(goalId).get();
        Workout toBeAddedWorkout = workoutRepository.findById(workoutId).get();
        toBePatchedGoal.getWorkouts().add(toBeAddedWorkout);
        goalRepository.save(toBePatchedGoal);
    }

    public void updateProfile(Long goalId, String profileId) {
        Goal toBePatchedGoal = goalRepository.findById(goalId).get();
        Profile toBeAddedProfile = profileRepository.findById(profileId).get();
        toBePatchedGoal.setProfile(toBeAddedProfile);
        goalRepository.save(toBePatchedGoal);
    }
}