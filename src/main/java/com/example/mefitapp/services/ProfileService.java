package com.example.mefitapp.services;

import com.example.mefitapp.models.Profile;
import com.example.mefitapp.models.Workout;
import com.example.mefitapp.repositories.ProfileRepository;
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

    public List<Workout> getWorkoutsByProfile(Long id) {
        Profile returnProfile = profileRepository.findById(id).get();
        return workoutRepository.findAllByProfiles(returnProfile);
    }
}
