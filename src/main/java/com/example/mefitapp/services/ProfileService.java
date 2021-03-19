package com.example.mefitapp.services;

import com.example.mefitapp.models.Goal;
import com.example.mefitapp.models.Profile;
import com.example.mefitapp.models.Program;
import com.example.mefitapp.models.Workout;
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

    public List<Workout> getWorkoutsByProfile(Long id) {
        Profile returnProfile = profileRepository.findById(id).get();
        return workoutRepository.findAllByProfiles(returnProfile);
    }

    public List<Program> getProgramsByProfile(Long id) {
        Profile returnProfile = profileRepository.findById(id).get();
        return programRepository.findAllByProfiles(returnProfile);
    }

    public List<Goal> getGoalsByProfile(Long id) {
        Profile returnProfile = profileRepository.findById(id).get();
        return goalRepository.findAllByProfiles(returnProfile);
    }
}
