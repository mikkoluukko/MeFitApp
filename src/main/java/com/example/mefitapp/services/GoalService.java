package com.example.mefitapp.services;

import com.example.mefitapp.models.*;
import com.example.mefitapp.repositories.*;
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
    private ProgramRepository programRepository;

    @Autowired
    private ProfileRepository profileRepository;

    public List<Workout> getWorkoutsByGoal(Long id) {
        Goal returnGoal = goalRepository.findById(id).get();
        return workoutRepository.findAllByGoals(returnGoal);
    }

    public List<Program> getProgramsByGoal(Long id) {
        Goal returnGoal = goalRepository.findById(id).get();
        return programRepository.findAllByGoals(returnGoal);
    }

    public void updateList(Object listName, Long goalId, String itemId) {
        switch (listName.toString()) {
            case "workouts" -> updateExerciseSets(goalId, Long.valueOf(itemId));
            case "programs" -> updatePrograms(goalId, Long.valueOf(itemId));
            case "profile" -> updateProfile(goalId, itemId);
        }
    }

    public void updateExerciseSets(Long goalId, Long workoutId) {
        Goal toBePatchedGoal = goalRepository.findById(goalId).get();
        Workout toBeAddedWorkout = workoutRepository.findById(workoutId).get();
        toBePatchedGoal.getWorkouts().add(toBeAddedWorkout);
        goalRepository.save(toBePatchedGoal);
    }

    public void updatePrograms(Long goalId, Long programId) {
        Goal toBePatchedGoal = goalRepository.findById(goalId).get();
        Program toBeAddedProgram = programRepository.findById(programId).get();
        toBePatchedGoal.getPrograms().add(toBeAddedProgram);
        goalRepository.save(toBePatchedGoal);
    }

    public void updateProfile(Long goalId, String profileId) {
        Goal toBePatchedGoal = goalRepository.findById(goalId).get();
        Profile toBeAddedProfile = profileRepository.findById(profileId).get();
        toBePatchedGoal.setProfile(toBeAddedProfile);
        goalRepository.save(toBePatchedGoal);
    }
}