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
    private ProfileRepository profileRepository;

    @Autowired
    private ProgramRepository programRepository;

    public List<Workout> getWorkoutsByGoal(Long id) {
        Goal returnGoal = goalRepository.findById(id).get();
        return workoutRepository.findAllByGoals(returnGoal);
    }

    public List<Program> getProgramsByGoal(Long id) {
        Goal returnGoal = goalRepository.findById(id).get();
        return programRepository.findAllByGoals(returnGoal);
    }
}