package com.example.mefitapp.services;

import com.example.mefitapp.models.ExerciseSet;
import com.example.mefitapp.models.Profile;
import com.example.mefitapp.models.Program;
import com.example.mefitapp.models.Workout;
import com.example.mefitapp.repositories.ExerciseSetRepository;
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

    public List<Profile> getProfilesByProgram(Long id) {
        Program returnProgram = programRepository.findById(id).get();
        return profileRepository.findAllByPrograms(returnProgram);
    }

    public List<Workout> getWorkoutsByProgram(Long id) {
        Program returnProgram = programRepository.findById(id).get();
        return workoutRepository.findAllByPrograms(returnProgram);
    }
}
