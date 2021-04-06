package com.example.mefitapp.services;

import com.example.mefitapp.models.*;
import com.example.mefitapp.repositories.GoalRepository;
import com.example.mefitapp.repositories.ProfileRepository;
import com.example.mefitapp.repositories.ProgramRepository;
import com.example.mefitapp.repositories.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgramService {
    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private GoalRepository goalRepository;

    public List<Profile> getProfilesByProgram(Long id) {
        Program returnProgram = programRepository.findById(id).get();
        return profileRepository.findAllByPrograms(returnProgram);
    }

    public List<Workout> getWorkoutsByProgram(Long id) {
        Program returnProgram = programRepository.findById(id).get();
        return workoutRepository.findAllByPrograms(returnProgram);
    }

    public List<Goal> getGoalsByProgram(Long id) {
        Program returnProgram = programRepository.findById(id).get();
        return goalRepository.findAllByPrograms(returnProgram);
    }

    public void updateList(Object listName, Long workoutId, String itemId) {
        switch (listName.toString()) {
            case "goals" -> updateGoals(workoutId, Long.valueOf(itemId));
            case "workouts" -> updateWorkouts(workoutId, Long.valueOf(itemId));
            case "profiles" -> updateProfiles(workoutId, itemId);
        }
    }

    public void updateGoals(Long programId, Long goalId) {
        Program toBePatchedProgram = programRepository.findById(programId).get();
        Goal toBeAddedGoal = goalRepository.findById(goalId).get();
        toBePatchedProgram.getGoals().add(toBeAddedGoal);
        programRepository.save(toBePatchedProgram);
    }

    public void updateWorkouts(Long programId, Long workoutId) {
        Program toBePatchedProgram = programRepository.findById(programId).get();
        Workout toBeAddedWorkout = workoutRepository.findById(workoutId).get();
        toBePatchedProgram.getWorkouts().add(toBeAddedWorkout);
        programRepository.save(toBePatchedProgram);
    }

    public void updateProfiles(Long programId, String profileId) {
        Program toBePatchedProgram = programRepository.findById(programId).get();
        Profile toBeAddedProfile = profileRepository.findById(profileId).get();
        toBePatchedProgram.getProfiles().add(toBeAddedProfile);
        programRepository.save(toBePatchedProgram);
    }
}
