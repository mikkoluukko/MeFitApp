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
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private GoalRepository goalRepository;

    public List<Workout> getWorkoutsByProfile(String id) {
        Profile returnProfile = profileRepository.findById(id).get();
        return workoutRepository.findAllByProfiles(returnProfile);
    }

    public List<Program> getProgramsByProfile(String id) {
        Profile returnProfile = profileRepository.findById(id).get();
        return programRepository.findAllByProfiles(returnProfile);
    }

    public List<Goal> getGoalsByProfile(String id) {
        Profile returnProfile = profileRepository.findById(id).get();
        return goalRepository.findAllByProfile(returnProfile);
    }

    public void updateList(Object listName, String profileId, String itemId) {
        switch (listName.toString()) {
            case "goals" -> updateGoals(profileId, Long.valueOf(itemId));
            case "programs" -> updatePrograms(profileId, Long.valueOf(itemId));
            case "workouts" -> updateWorkouts(profileId, Long.valueOf(itemId));
        }
    }

    public void updateGoals(String profileId, Long goalId) {
        Profile toBePatchedProfile = profileRepository.findById(profileId).get();
        Goal toBeAddedGoal = goalRepository.findById(goalId).get();
        toBePatchedProfile.getGoals().add(toBeAddedGoal);
        profileRepository.save(toBePatchedProfile);
    }

    public void updatePrograms(String profileId, Long programId) {
        Profile toBePatchedProfile = profileRepository.findById(profileId).get();
        Program toBeAddedProgram = programRepository.findById(programId).get();
        toBePatchedProfile.getPrograms().add(toBeAddedProgram);
        profileRepository.save(toBePatchedProfile);
    }

    public void updateWorkouts(String profileId, Long workoutId) {
        Profile toBePatchedProfile = profileRepository.findById(profileId).get();
        Workout toBeAddedWorkout = workoutRepository.findById(workoutId).get();
        toBePatchedProfile.getWorkouts().add(toBeAddedWorkout);
        profileRepository.save(toBePatchedProfile);
    }
}
